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

import org.unitedinternet.cosmo.dao.ModelValidationException;
import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.BooleanAttribute;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.QName;

///**
// * Hibernate persistent BooleanAtttribute.
// */
//@Entity
//@DiscriminatorValue("boolean")

//ORMlite persistent boolean attribute of some Item

public class OrmliteBooleanAttributeWrapper extends OrmliteAttributeWrapper implements BooleanAttribute {

    private static final long serialVersionUID = -8393344132524216261L;
    
    /** default constructor */
    public OrmliteBooleanAttributeWrapper() {
    	getPersistedAttribute().setAttributetype("boolean");
    }

    public OrmliteBooleanAttributeWrapper(QName qname, Boolean value) {
        setQName(qname);
        getPersistedAttribute().setBoolvalue(value);
        getPersistedAttribute().setAttributetype("boolean");
    }

    // Property accessors
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.hibernate.HibAttribute#getValue()
     */
    public Boolean getValue() {
        return getPersistedAttribute().getBoolvalue();
    }

    public Attribute copy() {
        BooleanAttribute attr = new OrmliteBooleanAttributeWrapper();
        attr.setQName(getQName().copyQName());
        attr.setValue(getValue());
        return attr;
    }

    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.BooleanAttribute#setValue(java.lang.Boolean)
     */
    public void setValue(Boolean value) {
    	getPersistedAttribute().setBoolvalue(value);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#setValue(java.lang.Object)
     */
    public void setValue(Object value) {
        if (value != null && !(value instanceof Boolean)) {
            throw new ModelValidationException(
                    "attempted to set non Boolean value on attribute");
        }
        setValue((Boolean) value);
    }
    
    /**
     * Convienence method for returning a Boolean value on a BooleanAttribute
     * with a given QName stored on the given item.
     * @param item item to fetch BooleanAttribute from
     * @param qname QName of attribute
     * @return Boolean value of IntegerAttribute
     */
    public static Boolean getValue(Item item, QName qname) {
    	OrmliteBooleanAttributeWrapper ba = new OrmliteBooleanAttributeWrapper();
    	ba.setPersistedAttribute((OrmliteAttribute)item.getAttribute(qname));
        return ba.getValue();
    }
    
    /**
     * Convienence method for setting a Boolean value on a BooleanAttribute
     * with a given QName stored on the given item.
     * @param item item to fetch BooleanAttribute from
     * @param qname QName of attribute
     * @param value value to set on BooleanAttribute
     */
    public static void setValue(Item item, QName qname, Boolean value) {
        BooleanAttribute attr = (BooleanAttribute) item.getAttribute(qname);
        if(attr==null && value!=null) {
            attr = new OrmliteBooleanAttributeWrapper(qname,value);
            System.out.println("[AGATE] attributetype of boolean wrapper = " +  ((OrmliteBooleanAttributeWrapper)attr).getPersistedAttribute().getAttributetype());
            System.out.println("[AGATE] attribute: " +  ((OrmliteBooleanAttributeWrapper)attr).getPersistedAttribute());
            item.addAttribute(((OrmliteBooleanAttributeWrapper)attr).getPersistedAttribute());
            return;
        }
        if(value==null) {
            item.removeAttribute(qname);
        }
        else {
            attr.setValue(value);
        }
    }

    @Override
    public void validate() {
//        // TODO Auto-generated method stub
//        
    }

    @Override
    public String calculateEntityTag() {
        return "";
    }

}
