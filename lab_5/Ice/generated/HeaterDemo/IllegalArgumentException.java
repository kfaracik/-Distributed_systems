//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.7
//
// <auto-generated>
//
// Generated from file `heater.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package HeaterDemo;

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
        return "::HeaterDemo::IllegalArgumentException";
    }

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::HeaterDemo::IllegalArgumentException", -1, true);
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
    public static final long serialVersionUID = 9050857L;
}