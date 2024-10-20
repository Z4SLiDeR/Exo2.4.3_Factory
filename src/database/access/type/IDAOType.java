package database.access.type;

import school.Type;

import java.util.ArrayList;

public interface IDAOType {
    Type addType(String name);
    boolean updateType(Type type);
    boolean deleteType(Type type);
    Type getTypeById(int typeId);
    ArrayList<Type> getTypes();
}
