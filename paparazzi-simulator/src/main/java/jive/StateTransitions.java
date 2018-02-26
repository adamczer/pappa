package jive;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class StateTransitions {
	Tree<Transition> trans;
	String[] oldvalues;
	private static int iterCount = 0;
	public TreeMap<String, ArrayList<Integer>> map = new TreeMap<String,ArrayList<Integer>>();
	public TreeMap<Integer, ArrayList<Double>> map1  = new TreeMap<Integer,ArrayList<Double>>();
	public Map<String, Integer> counter = new HashMap();
	
	public static void incrementIterCounter (){
		iterCount++;
	}
	//public static StateTransitions instance = new StateTransitions(new String[]{"Start"});
	
	public StateTransitions(int[] a) {
		int n = a.length;
		String[] newvalues = new String[n];
		oldvalues = new String[n];
		for (int i=0; i<n; i++) {
			newvalues[i] = a[i] + "";
			oldvalues[i] = "";
		}
		oldvalues[0] = "Start";
		trans = new Tree<Transition>(new Transition(oldvalues, newvalues));
		oldvalues = newvalues;
	}
	
	public StateTransitions(String[] newvalues) {
		int n = newvalues.length;
		oldvalues = new String[n];
		for (int i=0; i<n; i++) {
			oldvalues[i] = "";
		}
		oldvalues[0] = "Start";
		//trans = new Tree<Transition>(new Transition(oldvalues, newvalues));
		trans = new Tree<Transition>();
		oldvalues = newvalues;
	}
	
	public synchronized void add_transition(String[] newvalues) {
		Transition t = new Transition(oldvalues, newvalues);
		trans.insert(t);
		String str = oldvalues[0] + newvalues[0];
		oldvalues = newvalues;
		if(counter.containsKey(str)){
			int c = counter.get(str) + 1;
			counter.put(str, c);
		}else{
			counter.put(str, 1);
		}
		add_iteration(newvalues[0]);
	}
	
	public synchronized void add_iteration(String methodName){
		ArrayList temp = new ArrayList<Integer>();
		if(map.containsKey(methodName)){
			temp = map.get(methodName);
			temp.add(iterCount);
			map.put(methodName, temp);
		}else{
			if(temp.isEmpty()){
				temp.add(1);
				map.put(methodName,temp);
			}	
		}
		
	}
	
	synchronized void add_transition(int[] a) {
		int n = a.length;
		String[] newvalues = new String[n];
		for (int i=0; i<n; i++) {
			newvalues[i] = a[i] + "";
		}
		Transition t = new Transition(oldvalues, newvalues);
		trans.insert(t);
		oldvalues = newvalues;
	}
	
	public void print() {
		
		System.out.println("@startuml");
		trans.print();
		System.out.println("@enduml");
		
		print_map();
		//TODO print position
	}
	
	public synchronized void print_map(){
	
	
	try{
		PrintStream out = new PrintStream(new FileOutputStream("/home/manjusha/pappa/out.txt"));
		System.setOut(out);
	}catch(Exception e){
		System.out.print(e);
	}
		
	Set s = map.entrySet();
	Iterator iter = s.iterator();
	
	while(iter.hasNext()){
		Map.Entry m = (Map.Entry) iter.next();
		System.out.println("Key:" +m.getKey()+ "     Value:" +m.getValue());
	} 
	compareFunctions();
	print_map_two();
	
	}

	public void add_points(double lat, double lon, double alt) {
		ArrayList<Double> val = new ArrayList();
		val.add(lat);
		val.add(lon);
		val.add(alt);		
		map1.put(iterCount, val);
	}
	
	
	public synchronized void print_map_two(){
		try{
			PrintStream out = new PrintStream(new FileOutputStream("/home/manjusha/pappa/out1.txt"));
			System.setOut(out);
		}catch(Exception e){
			System.out.print(e);
		}
			
		Set s = map1.entrySet();
		Iterator iter = s.iterator();
		
		while(iter.hasNext()){
			Map.Entry m = (Map.Entry) iter.next();
			System.out.println("Key:" +m.getKey()+ "     Value:" +m.getValue());
		
		}
		}
	
	public synchronized void compareFunctions(){
		
		try{
			PrintStream out = new PrintStream(new FileOutputStream("/home/manjusha/pappa/freq.txt"));
			System.setOut(out);
		}catch(Exception e){
			System.out.print(e);
		}
	
		//Frequency of each function
		Set s = map.entrySet();
		Iterator iter = s.iterator();
		
		while(iter.hasNext()){
			Map.Entry m = (Map.Entry) iter.next();
			ArrayList<Double> val = (ArrayList<Double>) m.getValue();
			System.out.println("Key:" +m.getKey()+ "     Value:" +val.size());
		
		}
		
		Set s1 = counter.entrySet();
		Iterator iter1 = s1.iterator();
		
		while(iter1.hasNext()){
			Map.Entry m = (Map.Entry) iter1.next();
			System.out.println("Key:" +m.getKey()+ "     Value:" +m.getValue());
		
		}
		
		
		//Common 
	}
	
}


class Transition implements Comparable<Transition> {	 // need to compare transitions to avoid duplication

	public String[] oldvalues, newvalues;
	public Map<String, Map<String,Integer>> count = new HashMap();
	public Transition(String[] oldvalues, String[] newvalues) {
		this.oldvalues = oldvalues;
		this.newvalues = newvalues;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Transition t) {   		
		int n = oldvalues.length;
		int i = 0;
		
		while (i < n) {
			if (oldvalues[i].compareTo(t.oldvalues[i]) < 0){
				return -1;}
			else if (oldvalues[i].compareTo(t.oldvalues[i]) > 0){
				return 1;}
			else if (newvalues[i].compareTo(t.newvalues[i]) < 0){
				return -1;}
			else if (newvalues[i].compareTo(t.newvalues[i]) > 0){
				return 1;}
			else
				i++;
		}
		return 0;
	}

	public void print() { 				 // print out one transition
		System.out.print("(");
		int n = oldvalues.length;
		System.out.print(oldvalues[0]);
		if (oldvalues[0].compareTo("Start") != 0)
		   for (int i = 1; i < n; i++){
			  System.out.print("," + oldvalues[i]);}
	    System.out.print(") --> (");
	    System.out.print(newvalues[0]);
	    for (int i = 1; i < n; i++){
			System.out.print("," + newvalues[i]);}
 	    System.out.println(")");  
 	   /*
 	   if(count.containsKey(oldvalues[i])){
			if(count.get(oldvalues[i]).containsKey(newvalues[i])){
				int counter = count.get(oldvalues[i]).get(newvalues[i]) + 1;
				count.get(oldvalues[i]).put(newvalues[i], counter);
			}
			else{
				count.get(oldvalues[i]).put(newvalues[i], 1);
			}
		}else{
			count.put(oldvalues[i],null);
		}
 	   
 	   for (Entry<String, Map<String, Integer>> entry : count.entrySet()){
 		   for(Entry<String,Integer> innerEntry : entry.getValue().entrySet()){
 			   	String key    = innerEntry.getKey();
 			    Integer value  = innerEntry.getValue();
 			    
 			    System.out.println("Key: " +entry.getKey()+ "  " +innerEntry.getKey()+ "Value:" +innerEntry.getValue());
 		   }
 	   }
 	   
 	  */
 	 
	}
}

class Tree<T extends Comparable<T>> {    // maintain all transitions in a binary search tree

	public Tree(T v) {
		value = v;
		left = null;
		right = null;
	}
	public Tree() {
		value = null;
		left = null;
		right = null;
	}

	public void insert(T v) {
		if(value == null) 
			value =v;
		if (value.compareTo(v) == 0)
			return;
		if (value.compareTo(v) > 0)
			if (left == null)
				left = new Tree<T>(v);
			else
				left.insert(v);
		else if (value.compareTo(v) < 0)
			if (right == null)
				right = new Tree<T>(v);
			else
				right.insert(v);
	}

	public void print() {
		if (left != null)
			left.print();
		((Transition) value).print();
		if (right != null)
			right.print();
	}

	protected T value;
	protected Tree<T> left;
	protected Tree<T> right;
}
