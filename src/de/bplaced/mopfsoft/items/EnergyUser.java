package de.bplaced.mopfsoft.items;

public interface EnergyUser {

public int drainEnergy(int amount);
public int getEnergy();
public int getMaxEnergy();
public int addEnergy();
}
