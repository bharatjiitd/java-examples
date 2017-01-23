package guava.cache;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class GuavaCacheExample {

  public static LoadingCache<Class<?>, Set<String>> getClassMethodCache(
      String spec) {
    CacheLoader<Class<?>, Set<String>> loader = new CacheLoader<Class<?>, Set<String>>() {

      @Override
      public Set<String> load(Class<?> key) throws Exception {
        // TODO Auto-generated method stub
        Set<String> methodSet = new HashSet<String>();
        Method[] methods = key.getMethods();
        for (Method method : methods) {
          methodSet.add(method.toString());
        }
        return methodSet;
      }
    };

    RemovalListener<Class<?>, Set<String>> removalListener = new RemovalListener<Class<?>, Set<String>>() {

      @Override
      public void onRemoval(
          RemovalNotification<Class<?>, Set<String>> notification) {
        // TODO Auto-generated method stub
        System.out.println("key got removed = " + notification.getKey());
      }
    };
    
    return CacheBuilder.from(spec).removalListener(removalListener).build(loader);
  }
  
  
}
