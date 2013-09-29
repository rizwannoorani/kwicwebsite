/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Interface to enforce the implementation of
 *                      setData(), getIndex(),
 *                      lockIndex(), unlockIndex, isIndexLocked(),
 *                      printData(), and printIndex().
 *  File              OutputInterface.java
 *  Date:             9/24/2013
********************************************************************/

public interface OutputInterface
{
    void setData(LinkedQueue q);
    LinkedQueue getIndex();
    void lockIndex();
    void unlockIndex();
    boolean isIndexLocked();
    void printData(int i, LinkedQueue q);
    void printIndex();
}