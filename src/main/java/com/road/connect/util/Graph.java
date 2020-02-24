package com.road.connect.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
	private Map<String, List<String>> adjVertices;

	public Graph(){
		adjVertices = new LinkedHashMap<>();
	}

	public void addVertex(String label1, String label2) {
		adjVertices.putIfAbsent(label1, new ArrayList<>());
		adjVertices.putIfAbsent(label2, new ArrayList<>());
		adjVertices.get(label1).add(label2);
		adjVertices.get(label2).add(label1);
	}

	public Map<String, List<String>> getAdjVertices() {
		return adjVertices;
	}

	public void setAdjVertices(Map<String, List<String>> adjVertices) {
		this.adjVertices = adjVertices;
	}

	public boolean findPath(String label1,String label2){
		Set<String> visited = new HashSet<>();
		LinkedList<String> queue = new LinkedList();
		queue.add(label1);
		visited.add(label1);
		String current = null;
		while(queue.size()!=0){
			current = queue.poll();
			if(current.equals(label2)){
				return true;
			}
			if(adjVertices.get(current)!=null){
				for(String value: adjVertices.get(current)){
					if(!visited.contains(value)){
						queue.add(value);
						visited.add(current);
					}
				}
			}
		}
		return false;

	}

}