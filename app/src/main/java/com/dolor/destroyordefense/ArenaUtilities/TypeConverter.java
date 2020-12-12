package com.dolor.destroyordefense.ArenaUtilities;

class TypeConverter {
    public static int square;
    Type type;
    int value;

    public TypeConverter(Type type) {
        this.type = type;
    }

    public TypeConverter(int value, Type type) {
        this(type);
        this.value = value;
    }

    public TypeConverter(TypeConverter t2) {
        this(t2.value, t2.type);
    }

    int toPixel() {
        if (type == Type.point) {
            type = Type.pixel;
            value *= (double) (square) ;


        }
        return value;
    }

    int toPoint() {
        if (type == Type.pixel) {
            type = Type.point;
            value /= square;
        }
        return value;
    }

    public TypeConverter setValue(int value) {
        this.value = value;
        return this;
    }

    public TypeConverter setValue(int value, Type type) {
        setValue(value);
        this.type = type;
        return this;
    }

    public int toInteger() {
        return value;
    }

    public TypeConverter minus(TypeConverter t2) {
        TypeConverter temp = new TypeConverter(t2);
        temp.setValue(-temp.value);
        return plus(temp);
    }

    public TypeConverter plus(TypeConverter t2) {
        if (type != t2.type) {
            TypeConverter temp = new TypeConverter(t2);
            if (type == Type.pixel)
                temp.toPixel();
            else
                temp.toPoint();
        }
        return new TypeConverter(value + t2.value, type);
    }

    @Override
    public String toString() {
        return "(" + value + "," + type + ")";
    }
}