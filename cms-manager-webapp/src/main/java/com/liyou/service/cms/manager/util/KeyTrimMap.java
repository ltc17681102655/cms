package com.liyou.service.cms.manager.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by linxiaohui on 15/8/21.
 */
public class KeyTrimMap extends LinkedHashMap {
    @Override
    public Object put(Object key, Object value) {
        if( null != key && key instanceof String){
            key = ((String) key).trim();
        }
        if( value instanceof Map ){
            Map  _value = new KeyTrimMap();
            _value.putAll((Map)value);
            value=_value;
        }
        return super.put(key, value);
    }
}
