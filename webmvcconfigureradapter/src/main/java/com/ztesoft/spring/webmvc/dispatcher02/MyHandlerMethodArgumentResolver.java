package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author tian.lue
 */
public class MyHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(MyForm.class)) {
            return true;
        }
        return false;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
        NativeWebRequest nbRequest, WebDataBinderFactory factory) throws Exception {
        if (null == factory) {
            return null;
        }
        HttpServletRequest request = (HttpServletRequest) nbRequest.getNativeRequest();
        request.setCharacterEncoding("UTF-8");
        Class<?> targetType = parameter.getParameterType();
        MyForm myForm = parameter.getParameterAnnotation(MyForm.class);
        String prefix = getPrefix(myForm, targetType);
        return _resolveArgument(targetType, parameter, nbRequest, factory, request, prefix, 1);
    }

    private Object _resolveArgument(Class<?> targetType, MethodParameter parameter,
        NativeWebRequest nbRequest, WebDataBinderFactory factory, HttpServletRequest request,
        String prefix, int next) throws Exception {
        Object arg = new Object();
        // 获取 targetType下声明的属性
        Field[] fields = targetType.getDeclaredFields();
        Object target = targetType.newInstance();
        // 创建数据绑定
        WebDataBinder binder = factory.createBinder(nbRequest, null, prefix);
        for(Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();
            // 获取请求对象
            Map<String, String[]> valueMap = request.getParameterMap();
            boolean isSimple = BeanUtils.isSimpleProperty(fieldType); // 判断是否为简单类型：int String char......
            if (isSimple) { // 是简单类型
                String[] values = valueMap.get(prefix + "." + fieldName);
                if (next == 1) { // next  == 1 表示要绑定的属性对象不是集合类型
                    arg = binder.convertIfNecessary(values[0], fieldType, parameter);
                } else {
                    if (next - 1 > values.length) { // values的长度代码了该集合的 size
                        return null;
                    }
                    arg = binder.convertIfNecessary(values[next - 2], fieldType, parameter);
                }
            } else { // 复杂类型，如 Car 集合......
                if (Collection.class.isAssignableFrom(fieldType)) { // 是集合类型
                    // 注意，一定要存在对应的 getter方法
                    Method m  = target.getClass().getMethod("get" + captureName(fieldName), null);
                    // 获取集合的泛型类型
                    Type[] resultArgType = null;
                    Type resultType = m.getGenericReturnType();
                    if (resultType instanceof ParameterizedType
                        && ((ParameterizedType) resultType).getRawType() == java.util.List.class){
                        resultArgType = ParameterizedType.class.cast(resultType).getActualTypeArguments();
                    }
                    // 强制转为对应的 class类型
                    Class argClass = (Class) resultArgType[0];
                    if (BeanUtils.isSimpleProperty(argClass)) { // 判断集合的泛型是否是简单类型
                        arg = Arrays.asList(valueMap.get(prefix + "." + fieldName));
                    } else { // List<Car>
                        int loop = 2; // 设置标志位
                        Object obj = new Object();
                        List<Object> objList = new ArrayList<Object>();
                        while (null != obj) { // 当返回的对象为 null时，结束
                            obj =  _resolveArgument(argClass, parameter.nested(), nbRequest, factory, request,
                                prefix + "." + fieldName, loop);
                            objList.add(obj);
                            loop ++;
                        }
                        // 去除末尾的 null元素
                        objList.remove(objList.size() - 1);
                        arg = objList;
                    }
                } else {
                    arg =  _resolveArgument(fieldType, parameter.nested(), nbRequest, factory, request, prefix + "." + fieldName, 1);
                }
            }
            // 为属性设置值
            field.set(target, arg);
        }
        // 返回构造的对象
        return target;
    }

    private String getPrefix(MyForm myForm, Class<?> targetType) {
        String prefix = myForm.value();
        if(prefix.equals("")){
            prefix=getDefaultClassName(targetType);
        }
        return prefix;
    }

    /**
     * 首字母大写
     * @param name
     * @return
     */
    private String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= (cs[0] > 96 && cs[0] < 123) ? 32 : 0;
        return String.valueOf(cs);

    }

    private String getDefaultClassName(Class<?> targetType) {
        return ClassUtils.getShortNameAsProperty(targetType);
    }

    public MyHandlerMethodArgumentResolver() {
        super();
    }
}
