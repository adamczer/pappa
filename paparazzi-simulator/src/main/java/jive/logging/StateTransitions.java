package jive.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StateTransitions {
	Tree<Transition> trans;
	String[] oldvalues;
	private static int iterCount = 0;
	public HashMap<String,ArrayList<Integer>> map = new HashMap<String,ArrayList<Integer>>();
	
	public HashMap<Integer,ArrayList<Double>> map1  = new HashMap<Integer,ArrayList<Double>>();
	
	public static void incrementIterCounter (){
		iterCount++;
	}
	public static StateTransitions instance = new StateTransitions(new String[]{"Start"});
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
		oldvalues = newvalues;
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
	Set s = map.entrySet();
	Iterator iter = s.iterator();
	
	while(iter.hasNext()){
		Map.Entry m = (Map.Entry) iter.next();
		System.out.println("Key:" +m.getKey()+ "     Value:" +m.getValue());
	}
	}

	public void add_points(double lat, double lon, double alt) {
		ArrayList<Double> val = new ArrayList();
		val.add(lat);
		val.add(lon);
		val.add(alt);		
		map1.put(iterCount, val);
	}
}


class Transition implements Comparable<Transition> {	 // need to compare transitions to avoid duplication

	public String[] oldvalues, newvalues;

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
			if (oldvalues[i].compareTo(t.oldvalues[i]) < 0)
				return -1;
			else if (oldvalues[i].compareTo(t.oldvalues[i]) > 0)
				return 1;
			else if (newvalues[i].compareTo(t.newvalues[i]) < 0)
				return -1;
			else if (newvalues[i].compareTo(t.newvalues[i]) > 0)
				return 1;
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
		   for (int i = 1; i < n; i++)
			  System.out.print("," + oldvalues[i]);
	    System.out.print(") --> (");
	    System.out.print(newvalues[0]);
	    for (int i = 1; i < n; i++)
			System.out.print("," + newvalues[i]);
 	    System.out.println(")");
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
