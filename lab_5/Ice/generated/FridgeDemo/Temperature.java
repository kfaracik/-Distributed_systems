//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.7
//
// <auto-generated>
//
// Generated from file `fridge.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package FridgeDemo;

public class Temperature implements java.lang.Cloneable,
                                    java.io.Serializable
{
    public long id;

    public int temp;

    public Temperature()
    {
    }

    public Temperature(long id, int temp)
    {
        this.id = id;
        this.temp = temp;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        Temperature r = null;
        if(rhs instanceof Temperature)
        {
            r = (Temperature)rhs;
        }

        if(r != null)
        {
            if(this.id != r.id)
            {
                return false;
            }
            if(this.temp != r.temp)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::FridgeDemo::Temperature");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, id);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, temp);
        return h_;
    }

    public Temperature clone()
    {
        Temperature c = null;
        try
        {
            c = (Temperature)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeLong(this.id);
        ostr.writeInt(this.temp);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.id = istr.readLong();
        this.temp = istr.readInt();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, Temperature v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public Temperature ice_read(com.zeroc.Ice.InputStream istr)
    {
        Temperature v = new Temperature();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<Temperature> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, Temperature v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.VSize))
        {
            ostr.writeSize(12);
            ice_write(ostr, v);
        }
    }

    static public java.util.Optional<Temperature> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.VSize))
        {
            istr.skipSize();
            return java.util.Optional.of(Temperature.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final Temperature _nullMarshalValue = new Temperature();

    /** @hidden */
    public static final long serialVersionUID = -2143026978L;
}
