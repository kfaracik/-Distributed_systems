//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.7
//
// <auto-generated>
//
// Generated from file `global.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package GlobalDemo;

public class NoInput extends com.zeroc.Ice.UserException
{
    public NoInput()
    {
    }

    public NoInput(Throwable cause)
    {
        super(cause);
    }

    public String ice_id()
    {
        return "::GlobalDemo::NoInput";
    }

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::GlobalDemo::NoInput", -1, true);
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
    public static final long serialVersionUID = 312632472L;
}
