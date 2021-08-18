package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Models a Solar System in which the bounty hunter has to search for her target.
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class SolarSystem { 
    
    /**
     * ArrayList of LinkedLists for all planets
     */
    ArrayList<LinkedList<Planet>> planetList = new ArrayList<LinkedList<Planet>>();
    
    /**
     * Adds new planets to planetList.
     * @param str Edges to planets.
     * @param index Node of planet where edges direct out.
     */
    public void add(String str, int index) {
        String[] command = str.split(" ");
        boolean isLast = false;

        //Planet has no connections in direction to other planet(s)
        if (command.length == 1) {
            planetList.add(new LinkedList<Planet>());
            planetList.get(planetList.size() - 1).addLast(new Planet(false, 0));
        }
        
        //Planet has direct connections in direction to other planet(s)
        else {
            for (int i = 1; i < command.length; i++) {
                
                if (i + 1 >= command.length) {
                    isLast = true;
                }
                
                //No planet-lists added yet
                if (planetList.size() == 0) {
                    planetList.add(new LinkedList<Planet>());
                    planetList.get(0).addLast(new Planet(Integer.parseInt(command[i]), isLast));
                }
                
                else {
                    
                    //Planet fits at end of list
                    if (hasLast(index)) {
                        planetList.get(getLastIndex(index)).addLast(new
                                Planet(Integer.parseInt(command[i]), isLast));
                    }
                    else {
                        
                        //Planet fits at start of list
                        if (hasFirst(Integer.parseInt(command[i]))) {
                            planetList.get(getFirstIndex(Integer.parseInt(command[i]))).addFirst(new
                                    Planet(Integer.parseInt(command[i]), isLast));
                        }
                        else {
                            planetList.add(new LinkedList<Planet>());
                            planetList.get(planetList.size() - 1).addLast(new
                                    Planet(Integer.parseInt(command[i]), isLast));
                        }
                    }
                } 
            }    
        }
    }
    
    /**
     * Goes through all lists in planetList in search for cycles and returns
     * true, if all lists are cycle-free.
     * @return True if no cycles are found in any of the lists.
     */
    public boolean hasCycles() {
        boolean cycle = false;
        if (planetList == null) {
            return false;
        }
        
        boolean flag = false;
        int foundIndex = 0;
        
        for (int i = 0; i < planetList.size(); i++) {
            
            //Counter for times a planet appears in the list
            ArrayList<Index> counter = new ArrayList<Index>();
            for (int j = 0; j < planetList.get(i).size(); j++) {
                
                if (counter.size() == 0) {
                    counter.add(new Index(planetList.get(i).get(j).getIndex()));
                    counter.get(0).incrementCounter();
                }
                else {
                    for (int k = 0; k < counter.size(); k++) {
                        //Not last planet in list
                        if (j + 1 < planetList.get(i).size()) {
                            if (counter.get(k).getIndex() == planetList.get(i).get(j).getIndex()) {
                                foundIndex = counter.get(k).getIndex();
                                flag = true;
                                break;
                            }
                        }
                        //Last planet in list
                        else if (j + 1 >= planetList.get(i).size()) {
                            
                            if (counter.get(k).getIndex() == planetList.get(i).get(j).getIndex()) {
                                foundIndex = counter.get(k).getIndex();
                                counter.get(foundIndex).incrementCounter();
                                if (counter.get(foundIndex).getCounter() > 1) {
                                    cycle = true;
                                }
                            }
                            else {
                                foundIndex = planetList.get(i).get(j).getIndex();
                                counter.add(new Index(foundIndex));
                                counter.get(counter.size() - 1).incrementCounter();
                            }
                            //Next planet of last listed is already in counter
                            if (counter.get(k).getIndex() == planetList.get(i).get(j).getNext()) {
                                foundIndex = planetList.get(i).get(j).getIndex();
                                counter.get(foundIndex).incrementCounter();
                                if (counter.get(foundIndex).getCounter() > 1) {
                                    cycle = true;
                                }
                            }
                            break;
                        }
                        else {
                            foundIndex = planetList.get(i).get(j).getNext();
                        }
                        
                    }
                    if (flag) {
                        counter.get(foundIndex).incrementCounter();
                        
                        //Check cycle
                        if (counter.get(foundIndex).getCounter() > 1) {
                            cycle = true;
                        }
                    }
                    else if (cycle) {
                        break;
                    }
                    else {
                        counter.add(new Index(foundIndex));
                        counter.get(counter.size() - 1).incrementCounter();
                    }               
                }  
            }
        }
        return cycle;
    }
    
    /**
     * Compares two LinkedLists of Planets and sorts them in descending size.
     */
    Comparator<LinkedList<Planet>> comparator = new Comparator<LinkedList<Planet>>() {
        @Override
        public int compare(LinkedList<Planet> p1, LinkedList<Planet> p2) {
            return Integer.compare(p2.size(), p1.size());
        }
    };
    
    /**
     * Simulates the trip through all lists of planets and returns the amount
     * of necessary drones to reach all planets.
     * @return Amount of drones needed.
     */
    public int travel() {
        int nrOfDroids = 0;
        Collections.sort(planetList, comparator);
        
        for (int i = 0; i < planetList.size(); i++) {
            boolean flag = false;

            //Check if first planet has been visited
            if (!planetList.get(i).get(0).getVisit()) {
                planetList.get(i).get(0).visitedPlanet();
                flag = true;
            }
            
            for (int j = 1; j < planetList.get(i).size(); j++) {
                
                //Has next Planet
                if (planetList.get(i).get(j).getHasNext()) {
                    if (!planetList.get(i).get(j).getVisit()) {
                        visitPlanet(planetList.get(i).get(j).getIndex());
                        flag = true;
                    }
                    
                    //Find index i and j of the planet in planetList
                    int nextI = 0;
                    int nextJ = 0;
                    for (int k = 0; k < planetList.size(); k++) {
                        for (int l = 0; l < planetList.get(k).size(); l++) {
                            if (planetList.get(i).get(j).getNext() == planetList.get(k).get(l).getIndex()) {
                                nextI = k;
                                nextJ = l;
                                break;
                            }
                        }
                    }
                    
                    //Visit next Planet
                    if (!planetList.get(nextI).get(nextJ).getVisit()) {
                        visitPlanet(planetList.get(nextI).get(nextJ).getIndex());
                        flag = true;
                    }
                }
                
                //Doesn't have next Planet
                else {
                    if (!planetList.get(i).get(j).getVisit()) {
                        visitPlanet(planetList.get(i).get(j).getIndex());
                        flag = true;
                    }
                }
            }
            
            if (flag) {
                nrOfDroids++;
            }
        }
        return nrOfDroids;
    }
    
    /**
     * Visits the planet of a given index.
     * @param planetIndex Index of the Planet to visit.
     */
    public void visitPlanet(int planetIndex) {
        for (int i = 0; i < planetList.size(); i++) {
            for (int j = 0; j < planetList.get(i).size(); j++) {
                
                if (planetList.get(i).get(j).getIndex() == planetIndex) {
                    planetList.get(i).get(j).visitedPlanet();
                }
            }
        }
    }
    
    /**
     * Checks if planets match at the end of the list.
     * @param index Index of the planet to be added to the list.
     * @return True if both planets match.
     */
    public boolean hasLast(int index) {
        boolean flag = false;
        for (int i = 0; i < planetList.size(); i++) {
            if (planetList.get(i).getLast().getNext() == index) {
                flag = true;
            }
        }
        return flag;
    }
    
    /**
     * Checks if planets match at the beginning of the list.
     * @param index Index of the planet to be added to the list.
     * @return True if both planets match.
     */
    public boolean hasFirst(int index) {
        boolean flag = false;
        for (int i = 0; i < planetList.size(); i++) {
            if (planetList.get(i).getFirst().getIndex() == index) {
                flag = true;
            }
        }
        return flag;
    }
    
    /**
     * Searches for index of last planet in the list that matches
     * with the new planet to be added.
     * @param index Index of the planet to be added.
     * @return Index of the last planet in the list that matches
     * with the new planet to be added.
     */
    public int getLastIndex(int index) {
        int match = 0;
        for (int i = 0; i < planetList.size(); i++) {
            if (planetList.get(i).getLast().getNext() == index) {
                match = i;
            }
        }
        return match;
    }
    
    /**
     * Searches for index of first planet in the list that matches
     * with the new planet to be added.
     * @param index Index of the planet to be added.
     * @return Index of the first planet in the list that matches
     * with the new planet to be added.
     */
    public int getFirstIndex(int index) {
        int match = 0;
        for (int i = 0; i < planetList.size(); i++) {
            if (planetList.get(i).getFirst().getIndex() == index) {
                match = i;
            }
        }
        return match;
    }
}