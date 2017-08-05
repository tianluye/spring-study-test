Spring中提供了各种 Aware接口，方便从上下文中获取当前的运行环境。

如果某个类实现了 Aware接口，那么就要重写它的 setXxx方法，正是这个方法，
让我们能够获取到当前的运行环境。

通过断点，分析下 Spring的上下文获取 bean流程：

- getBean() 上下文或者工厂获取 bean实例
- 该 bean属性的 setXxx方法
- 包装 bean(AbstractAutowireCapableBeanFactory.initializeBean())
    - 反射调用实现了 Aware接口的 setXxx方法 (invokeAwareMethods())
    - 调用实现了 BeanPostProcessor接口的 postProcessBeforeInitialization和 postProcessAfterInitialization方法

Spring是何时，如何去调用实现了 Aware接口的 setXxx方法的呢？

##### 1、BeanNameAware、BeanClassLoaderAware、BeanFactoryAware

这三个实现了 Aware接口的类的实现是在 AbstractAutowireCapableBeanFactory的 initializeBean方法中。

```java
protected Object initializeBean(final String beanName, final Object bean, RootBeanDefinition mbd) {
    if(System.getSecurityManager() != null) {
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                AbstractAutowireCapableBeanFactory.this.invokeAwareMethods(beanName, bean);
                return null;
            }
        }, this.getAccessControlContext());
    } else {
        // 调用 Aware的 setXxx方法，在 bean初始化之前
        this.invokeAwareMethods(beanName, bean);
    }

    Object wrappedBean = bean;
    if(mbd == null || !mbd.isSynthetic()) {
        wrappedBean = this.applyBeanPostProcessorsBeforeInitialization(bean, beanName);
    }

    try {
        this.invokeInitMethods(beanName, wrappedBean, mbd);
    } catch (Throwable var6) {
            throw new BeanCreationException(mbd != null?mbd.getResourceDescription():null, beanName, "Invocation of init method failed", var6);
    }

    if(mbd == null || !mbd.isSynthetic()) {
        wrappedBean = this.applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
    }

    return wrappedBean;
}

private void invokeAwareMethods(String beanName, Object bean) {
    if(bean instanceof Aware) {
        if(bean instanceof BeanNameAware) {
            ((BeanNameAware)bean).setBeanName(beanName);
        }

        if(bean instanceof BeanClassLoaderAware) {
            ((BeanClassLoaderAware)bean).setBeanClassLoader(this.getBeanClassLoader());
        }

        if(bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware)bean).setBeanFactory(this);
        }
    }
}

```

##### 2、其他实现了 Aware接口的类，如 ApplicationContextAware

这些类的调用 setXxx方法，是在实现了 BeanPostProcessor接口的 ApplicationContextAwareProcessor类的
postProcessBeforeInitialization方法里面定义。

```java
class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final ConfigurableApplicationContext applicationContext;
    private final StringValueResolver embeddedValueResolver;

    public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.embeddedValueResolver = new EmbeddedValueResolver(applicationContext.getBeanFactory());
    }

    public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
        AccessControlContext acc = null;
        if(System.getSecurityManager() != null && (bean instanceof EnvironmentAware || bean instanceof EmbeddedValueResolverAware || bean instanceof ResourceLoaderAware || bean instanceof ApplicationEventPublisherAware || bean instanceof MessageSourceAware || bean instanceof ApplicationContextAware)) {
            acc = this.applicationContext.getBeanFactory().getAccessControlContext();
        }

        if(acc != null) {
            AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                    ApplicationContextAwareProcessor.this.invokeAwareInterfaces(bean);
                    return null;
                }
            }, acc);
        } else {
            this.invokeAwareInterfaces(bean);
        }

        return bean;
    }

    private void invokeAwareInterfaces(Object bean) {
        if(bean instanceof Aware) {
            if(bean instanceof EnvironmentAware) {
                ((EnvironmentAware)bean).setEnvironment(this.applicationContext.getEnvironment());
            }

            if(bean instanceof EmbeddedValueResolverAware) {
                ((EmbeddedValueResolverAware)bean).setEmbeddedValueResolver(this.embeddedValueResolver);
            }

            if(bean instanceof ResourceLoaderAware) {
                ((ResourceLoaderAware)bean).setResourceLoader(this.applicationContext);
            }

            if(bean instanceof ApplicationEventPublisherAware) {
                ((ApplicationEventPublisherAware)bean).setApplicationEventPublisher(this.applicationContext);
            }

            if(bean instanceof MessageSourceAware) {
                ((MessageSourceAware)bean).setMessageSource(this.applicationContext);
            }

            if(bean instanceof ApplicationContextAware) {
                ((ApplicationContextAware)bean).setApplicationContext(this.applicationContext);
            }
        }

    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
```

那么容器是在什么时候把ApplicationContextAwareProcessor的对象注册到context的BeanPostProcessor列表中的呢 ?
在org.springframework.context.support.AbstractApplicationContext.prepareBeanFactory方法中：

```java
    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.setBeanClassLoader(this.getClassLoader());
        beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));
        beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, this.getEnvironment()));

        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        beanFactory.ignoreDependencyInterface(ResourceLoaderAware.class);
        beanFactory.ignoreDependencyInterface(ApplicationEventPublisherAware.class);
        beanFactory.ignoreDependencyInterface(MessageSourceAware.class);
        beanFactory.ignoreDependencyInterface(ApplicationContextAware.class);
        beanFactory.ignoreDependencyInterface(EnvironmentAware.class);
        beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
        beanFactory.registerResolvableDependency(ResourceLoader.class, this);
        beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
        beanFactory.registerResolvableDependency(ApplicationContext.class, this);
        if(beanFactory.containsBean("loadTimeWeaver")) {
            beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));
            beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));
        }

        if(!beanFactory.containsLocalBean("environment")) {
            beanFactory.registerSingleton("environment", this.getEnvironment());
        }

        if(!beanFactory.containsLocalBean("systemProperties")) {
            beanFactory.registerSingleton("systemProperties", this.getEnvironment().getSystemProperties());
        }

        if(!beanFactory.containsLocalBean("systemEnvironment")) {
            beanFactory.registerSingleton("systemEnvironment", this.getEnvironment().getSystemEnvironment());
        }

    }
```

根据上面的过程，我们可以自己实现一个 Aware接口：

- 创建一个 Aware接口，包含了 setXxx方法
- 创建一个实现了 BeanPostProcessor接口的类，重写 postProcessBeforeInitialization方法
- 创建一个实现了刚才定义的 Aware接口的类，重写 setXxx方法
- 获取 beanFactory，工厂是实现了这个 ConfigurableBeanFactory接口的类
- 调用 addBeanPostProcessor方法，将自定义的 BeanPostProcessor加入到工厂中

当我们调用了实现了自定义的 Aware接口的时候，就会调用 setXxx方法将值注入进来。

注意：
在早期的版本，获取 ConfigurableBeanFactory工厂实例的时候，是通过执行这个语句：
ConfigurableBeanFactory factory = new XmlBeanFactory(new ClassPathResource("spring-aware.xml"));
但是现在版本已经过期，使用 DefaultListableBeanFactory(ConfigurableBeanFactory的实现类)和 XmlBeanDefinitionReader代替

```java
Resource resource = new ClassPathResource("spring-aware.xml");
DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
reader.loadBeanDefinitions(resource);
```
其实这段代码就是上面过期 XmlBeanFactory的构造函数的具体实现。

```java
@Deprecated
public class XmlBeanFactory extends DefaultListableBeanFactory {
    private final XmlBeanDefinitionReader reader;

    public XmlBeanFactory(Resource resource) throws BeansException {
        this(resource, (BeanFactory)null);
    }

    public XmlBeanFactory(Resource resource, BeanFactory parentBeanFactory) throws BeansException {
        super(parentBeanFactory);
        this.reader = new XmlBeanDefinitionReader(this);
        this.reader.loadBeanDefinitions(resource);
    }
}
```


