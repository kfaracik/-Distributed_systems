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

public interface HeaterIcePrx extends com.zeroc.Ice.ObjectPrx
{
    default void setHeat(Temperature data)
        throws IllegalArgumentException
    {
        setHeat(data, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void setHeat(Temperature data, java.util.Map<String, String> context)
        throws IllegalArgumentException
    {
        try
        {
            _iceI_setHeatAsync(data, context, true).waitForResponseOrUserEx();
        }
        catch(IllegalArgumentException ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<Void> setHeatAsync(Temperature data)
    {
        return _iceI_setHeatAsync(data, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> setHeatAsync(Temperature data, java.util.Map<String, String> context)
    {
        return _iceI_setHeatAsync(data, context, false);
    }

    /**
     * @hidden
     * @param iceP_data -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_setHeatAsync(Temperature iceP_data, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "setHeat", null, sync, _iceE_setHeat);
        f.invoke(true, context, null, ostr -> {
                     Temperature.ice_write(ostr, iceP_data);
                 }, null);
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_setHeat =
    {
        IllegalArgumentException.class
    };

    default void setHeatingPlan(TemperaturePlan data)
        throws IllegalArgumentException
    {
        setHeatingPlan(data, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void setHeatingPlan(TemperaturePlan data, java.util.Map<String, String> context)
        throws IllegalArgumentException
    {
        try
        {
            _iceI_setHeatingPlanAsync(data, context, true).waitForResponseOrUserEx();
        }
        catch(IllegalArgumentException ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<Void> setHeatingPlanAsync(TemperaturePlan data)
    {
        return _iceI_setHeatingPlanAsync(data, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> setHeatingPlanAsync(TemperaturePlan data, java.util.Map<String, String> context)
    {
        return _iceI_setHeatingPlanAsync(data, context, false);
    }

    /**
     * @hidden
     * @param iceP_data -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_setHeatingPlanAsync(TemperaturePlan iceP_data, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "setHeatingPlan", null, sync, _iceE_setHeatingPlan);
        f.invoke(true, context, null, ostr -> {
                     TemperaturePlan.ice_write(ostr, iceP_data);
                 }, null);
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_setHeatingPlan =
    {
        IllegalArgumentException.class
    };

    default String getHeaterState(long id)
        throws IllegalArgumentException
    {
        return getHeaterState(id, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default String getHeaterState(long id, java.util.Map<String, String> context)
        throws IllegalArgumentException
    {
        try
        {
            return _iceI_getHeaterStateAsync(id, context, true).waitForResponseOrUserEx();
        }
        catch(IllegalArgumentException ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> getHeaterStateAsync(long id)
    {
        return _iceI_getHeaterStateAsync(id, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> getHeaterStateAsync(long id, java.util.Map<String, String> context)
    {
        return _iceI_getHeaterStateAsync(id, context, false);
    }

    /**
     * @hidden
     * @param iceP_id -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.String> _iceI_getHeaterStateAsync(long iceP_id, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.String> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getHeaterState", null, sync, _iceE_getHeaterState);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeLong(iceP_id);
                 }, istr -> {
                     String ret;
                     ret = istr.readString();
                     return ret;
                 });
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_getHeaterState =
    {
        IllegalArgumentException.class
    };

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static HeaterIcePrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), HeaterIcePrx.class, _HeaterIcePrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static HeaterIcePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), HeaterIcePrx.class, _HeaterIcePrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static HeaterIcePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), HeaterIcePrx.class, _HeaterIcePrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static HeaterIcePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), HeaterIcePrx.class, _HeaterIcePrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static HeaterIcePrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, HeaterIcePrx.class, _HeaterIcePrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static HeaterIcePrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, HeaterIcePrx.class, _HeaterIcePrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default HeaterIcePrx ice_context(java.util.Map<String, String> newContext)
    {
        return (HeaterIcePrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default HeaterIcePrx ice_adapterId(String newAdapterId)
    {
        return (HeaterIcePrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default HeaterIcePrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (HeaterIcePrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default HeaterIcePrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (HeaterIcePrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default HeaterIcePrx ice_invocationTimeout(int newTimeout)
    {
        return (HeaterIcePrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default HeaterIcePrx ice_connectionCached(boolean newCache)
    {
        return (HeaterIcePrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default HeaterIcePrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (HeaterIcePrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default HeaterIcePrx ice_secure(boolean b)
    {
        return (HeaterIcePrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default HeaterIcePrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (HeaterIcePrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default HeaterIcePrx ice_preferSecure(boolean b)
    {
        return (HeaterIcePrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default HeaterIcePrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (HeaterIcePrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default HeaterIcePrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (HeaterIcePrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default HeaterIcePrx ice_collocationOptimized(boolean b)
    {
        return (HeaterIcePrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default HeaterIcePrx ice_twoway()
    {
        return (HeaterIcePrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default HeaterIcePrx ice_oneway()
    {
        return (HeaterIcePrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default HeaterIcePrx ice_batchOneway()
    {
        return (HeaterIcePrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default HeaterIcePrx ice_datagram()
    {
        return (HeaterIcePrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default HeaterIcePrx ice_batchDatagram()
    {
        return (HeaterIcePrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default HeaterIcePrx ice_compress(boolean co)
    {
        return (HeaterIcePrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default HeaterIcePrx ice_timeout(int t)
    {
        return (HeaterIcePrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default HeaterIcePrx ice_connectionId(String connectionId)
    {
        return (HeaterIcePrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default HeaterIcePrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (HeaterIcePrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::HeaterDemo::HeaterIce";
    }
}
