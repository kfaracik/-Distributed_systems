//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.7
//
// <auto-generated>
//
// Generated from file `sensor.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package SensorDemo;

public class IllegalArgumentException extends com.zeroc.Ice.UserException
{
    public IllegalArgumentException()
    {
    }

    public IllegalArgumentException(Throwable cause)
    {
        super(cause);
    }

    public String ice_id()
    {
        return "::SensorDemo::IllegalArgumentException";
    }

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::SensorDemo::IllegalArgumentException", -1, true);
        ostr_.endSlice();
    }

    /** @hidden */
    @Override
    protected void _readImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        istr_.endSlice();
    }

    /** @hidden */
    public static final long serialVersionUID = -1651186768L;
}
