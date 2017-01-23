package guava.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.testing.FakeTicker;

public class TestGuavaCacheLibrary {

  @Test
  public void sizeBasedEviction() {
    String spec = "maximumSize=5";
    
    LoadingCache<Class<?>, Set<String>> maxSizeSpecifiedCache = GuavaCacheExample.getClassMethodCache(spec);
    try {
      maxSizeSpecifiedCache.get(String.class);
      maxSizeSpecifiedCache.get(ArrayList.class);
      maxSizeSpecifiedCache.get(LinkedList.class);
      maxSizeSpecifiedCache.get(HashSet.class);
      maxSizeSpecifiedCache.get(HashMap.class);
    } catch (ExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
        
    assertEquals(5, maxSizeSpecifiedCache.size());
    
    assertNotNull(maxSizeSpecifiedCache.getIfPresent(String.class));
    
    assertNull(maxSizeSpecifiedCache.getIfPresent(LinkedHashMap.class));
    
    maxSizeSpecifiedCache.getUnchecked(TreeSet.class);
    maxSizeSpecifiedCache.getUnchecked(ArrayList.class);
    
    assertEquals(5, maxSizeSpecifiedCache.size());
    
    assertNotNull(maxSizeSpecifiedCache.getIfPresent(TreeSet.class));
    assertNotNull(maxSizeSpecifiedCache.getIfPresent(ArrayList.class));
  }
  
  
  @Test
  public void expirationBasedEviction() {
    FakeTicker ticker = new FakeTicker();
    
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

    
    LoadingCache<Class<?>, Set<String>> expirationBasedCache = CacheBuilder.newBuilder().
        expireAfterWrite(1, TimeUnit.MINUTES).ticker(ticker).build(loader);

    expirationBasedCache.getUnchecked(String.class);
    
    assertEquals(expirationBasedCache.size(), 1);
    assertNotNull(expirationBasedCache.getIfPresent(String.class));
    
    ticker.advance(1, TimeUnit.MINUTES);
    
    assertNull(expirationBasedCache.getIfPresent(String.class));
        
  }
}
