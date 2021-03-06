/*
 * Copyright 2006 Open Source Applications Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.unitedinternet.cosmo.model.ormlite;

//import javax.persistence.Column;
//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;
//
//import org.hibernate.validator.constraints.Length;
import org.unitedinternet.cosmo.dao.ModelValidationException;
import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.DataSizeException;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.QName;
import org.unitedinternet.cosmo.model.StringAttribute;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


///**
// * Hibernate persistent StringAttribute.
// */
//@Entity
//@DiscriminatorValue("string")

public class OrmliteStringAttributeWrapper extends OrmliteAttributeWrapper implements  StringAttribute {

    public static final int VALUE_LEN_MAX = 2048;
    
    private static final long serialVersionUID = 2417093506524504993L;

    // Constructors

    /** default constructor */
    public OrmliteStringAttributeWrapper() {
    	getPersistedAttribute().setAttributetype("string");
    }

    public OrmliteStringAttributeWrapper(QName qname, String value) {
        setQName(qname);
        getPersistedAttribute().setStringvalue(value);
    	getPersistedAttribute().setAttributetype("string");
    }

    // Property accessors
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#getValue()
     */
    public String getValue() {
        return getPersistedAttribute().getStringvalue();
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.StringAttribute#setValue(java.lang.String)
     */
    public void setValue(String value) {
    	getPersistedAttribute().setStringvalue(value);
    }
    
    public void setValue(Object value) {
        if (value != null && !(value instanceof String)) {
            throw new ModelValidationException(
                    "attempted to set non String value on attribute");
        }
        setValue((String) value);
    }
    
    public Attribute copy() {
        StringAttribute attr = new OrmliteStringAttributeWrapper();
        attr.setQName(getQName().copyQName());
        attr.setValue(getValue());
        return attr;
    }
    
    /**
     * Convienence method for returning a String value on a StringAttribute
     * with a given QName stored on the given item.
     * @param item item to fetch StringAttribute from
     * @param qname QName of attribute
     * @return String value of StringAttribute
     */
    public static String getValue(Item item, QName qname) {
    	OrmliteStringAttributeWrapper sa = new OrmliteStringAttributeWrapper();
    	sa.setPersistedAttribute((OrmliteAttribute)item.getAttribute(qname));
        return sa.getValue();
    }
    
    /**
     * Convienence method for setting a String value on a StringAttribute
     * with a given QName stored on the given item.
     * @param item item to fetch StringAttribute from
     * @param qname QName of attribute
     * @param value value to set on StringAttribute
     */
    public static void setValue(Item item, QName qname, String value) {
        StringAttribute attr = (StringAttribute) item.getAttribute(qname);
        if(attr==null && value!=null) {
            attr = new OrmliteStringAttributeWrapper(qname,value);
            ((OrmliteStringAttributeWrapper)attr).validate();
            item.addAttribute(((OrmliteStringAttributeWrapper)attr).getPersistedAttribute());
            return;
        }
        if(value==null) {
            item.removeAttribute(qname);
        }
        else {
            attr.setValue(value);
        }
    }
    
    //@Override
    public void validate() {
        if (getValue()!= null && getValue().length() > VALUE_LEN_MAX) {
            throw new DataSizeException("String attribute " + getQName() + " too large");
        }
    }

    @Override
    public String calculateEntityTag() {
        return "";
    }
}
