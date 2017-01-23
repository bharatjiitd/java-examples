package guava.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumMultiset;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

public class TestGuavaLibrary {

  @Test
  public void testMultiSet() {
    Multiset<String> fruitMultiSet = LinkedHashMultiset.create();
    String aNullFruit = null;

    fruitMultiSet.add("pineapple");
    fruitMultiSet.add("banana");
    fruitMultiSet.add("orange");
    fruitMultiSet.add("apple");
    fruitMultiSet.add("orange");
    fruitMultiSet.add("orange");
    fruitMultiSet.add("apple");
    fruitMultiSet.add(aNullFruit);

    assertEquals(fruitMultiSet.size(), 8);
    
    assertEquals(fruitMultiSet.count(aNullFruit), 1);
    
    Set<String> distinctFruits = fruitMultiSet.elementSet();
    
    assertEquals(distinctFruits.size(), 5);
    

    fruitMultiSet.remove("orange", 2) ;
    
    assertEquals(fruitMultiSet.count("orange"), 1);
    
    assertEquals(distinctFruits.size(), 5);
    
    fruitMultiSet.remove("orange", 2) ;
    
    assertEquals(fruitMultiSet.count("orange"), 0);
    
    assertEquals(distinctFruits.size(), 4);
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testImmutableMultiSet() {
    ImmutableMultiset<String> fruitset = new ImmutableMultiset.Builder<String>().
        add("orange").add("pineapple").add("apple", "banana", "kiwi", "papaya").build();
    try {
      fruitset.remove("orange");
      fail("method did not throw exception ... ");
    } catch (Exception e) {
      assertTrue(e instanceof UnsupportedOperationException);
    }
    
    ImmutableMultiset<String> bestLangImmutableSet = ImmutableMultiset.of("JAVA");
    
    assertEquals(bestLangImmutableSet.size(), 1);
    
    String[] processStates = {"New", "Ready", "Waiting", "Running", "Terminated"};
    
    ImmutableMultiset<String> processStateImmutableSet = ImmutableMultiset.copyOf(processStates);
    
    assertEquals(processStateImmutableSet.size(), 5);
  }
  
  enum State { New, Ready, Waiting, Running, Terminated}; 
  
  @Test
  public void testEnumMultiset() {
    EnumMultiset<State> stateEnumSet = EnumMultiset.create(State.class);
    
    stateEnumSet.add(State.New);
    stateEnumSet.add(State.Ready);
    stateEnumSet.add(State.Running);
    stateEnumSet.add(State.Waiting);
    stateEnumSet.add(State.Ready);
    stateEnumSet.add(State.Running);
    stateEnumSet.add(State.Terminated);
    
    assertEquals(stateEnumSet.size(), 7);
    
  }
  
  @Test
  public void testMultiMaps() {
    Multimap<String, String> fruitStoreMap = HashMultimap.create();
    fruitStoreMap.put("orange", "Target");
    fruitStoreMap.put("orange", "Walmart");
    fruitStoreMap.put("orange", "Reliance");
    fruitStoreMap.put("apple", "Target");
    fruitStoreMap.put("apple", "Walmart");
    fruitStoreMap.put("pineapple", "Reliance");
    fruitStoreMap.put("banana", "Target");
    fruitStoreMap.put("orange", "Walmart");
    fruitStoreMap.put(null, "Walmart");
    fruitStoreMap.put("kiwi", null);
    
    assertEquals(fruitStoreMap.get("orange").size(), 3);
    
    assertTrue(fruitStoreMap.containsValue("Reliance"));
    
    assertEquals(fruitStoreMap.keys().size(), 9);
    
    assertTrue(fruitStoreMap.containsKey(null));
    
    assertTrue(fruitStoreMap.containsValue(null));
  }
  
  @Test
  public void testBiMap() {
    BiMap<String, Integer> racePositionByName = HashBiMap.create();
    
    racePositionByName.put("Bharat", 1);
    racePositionByName.put("Andy", 2);
    racePositionByName.put("Ramesh", 3);
    racePositionByName.put("Suresh", 4);
    racePositionByName.put("Rajesh", 5);
    racePositionByName.put("Himesh", 6);
    
    BiMap<Integer, String> racePosition = racePositionByName.inverse();
    
    assertEquals(racePosition.get(1), "Bharat");
    
    assertNotNull(racePositionByName.get("Suresh"));
    
    racePositionByName.forcePut("Ramesh", 4);
    
    assertEquals(4, racePositionByName.get("Ramesh").intValue());
    
    assertNull(racePositionByName.get("Suresh"));
    
    assertNull(racePosition.get(3));
  }
  

  public static void main(String[] args) {
  }

}
