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

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.OneToMany;
//
//import org.hibernate.annotations.Cascade;
//import org.hibernate.annotations.CascadeType;
import org.unitedinternet.cosmo.model.CollectionItem;
import org.unitedinternet.cosmo.model.CollectionItemDetails;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.ItemTombstone;
import org.unitedinternet.cosmo.model.QName;
import org.unitedinternet.cosmo.model.Stamp;
import org.unitedinternet.cosmo.model.Ticket;
import org.unitedinternet.cosmo.model.Tombstone;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

///**
// * Hibernate persistent CollectionItem.
// */
//@Entity
//@DiscriminatorValue("collection")

public class OrmliteCollectionItemWrapper extends OrmliteItemWrapper implements CollectionItem {

    private static final long serialVersionUID = 2873258323314048223L;

    // CollectionItem specific attributes
    public static final QName ATTR_EXCLUDE_FREE_BUSY_ROLLUP =
            new OrmliteQName(CollectionItem.class, "excludeFreeBusyRollup");

    public static final QName ATTR_HUE =
            new OrmliteQName(CollectionItem.class, "hue");

    private transient Set<Item> children = null;

    public OrmliteCollectionItemWrapper() {
    	getPersistedItem().setItemtype("collection");
    };

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.CollectionItem#getChildren()
     */
    public Set<Item> getChildren() {
        if(children!=null) {
            return children;
        }

        children = new HashSet<Item>();
        for(OrmliteCollectionItemDetails cid: getPersistedItem().getChildDetails()) {
            children.add(cid.getItem());
        }

        children = Collections.unmodifiableSet(children);

        return children;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.CollectionItem#getChildDetails(org.unitedinternet.cosmo.model.Item)
     */
    public CollectionItemDetails getChildDetails(Item item) {
        for(CollectionItemDetails cid: getPersistedItem().getChildDetails()) {
            if(cid.getItem().getUid().equals(item.getUid()) && 
                    cid.getItem().getName().equals(item.getName())&& 
                    cid.getItem().getOwner().getUid().equals(item.getOwner().getUid()) ) {
                return cid;
            }
        }

        return null;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.CollectionItem#getChild(java.lang.String)
     */
    public Item getChild(String uid) {
        for (Item child : getChildren()) {
            if (child.getUid().equals(uid)) {
                return child;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.CollectionItem#getChildByName(java.lang.String)
     */
    public Item getChildByName(String name) {
        for (Item child : getChildren()) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.CollectionItem#isExcludeFreeBusyRollup()
     */
    public boolean isExcludeFreeBusyRollup() {
        Boolean bv =  OrmliteBooleanAttributeWrapper.getValue(getPersistedItem(), ATTR_EXCLUDE_FREE_BUSY_ROLLUP);
        if(bv==null) {
            return false;
        }
        else {
            return bv.booleanValue();
        }
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.CollectionItem#setExcludeFreeBusyRollup(boolean)
     */
    public void setExcludeFreeBusyRollup(boolean flag) {
        OrmliteBooleanAttributeWrapper.setValue(getPersistedItem(), ATTR_EXCLUDE_FREE_BUSY_ROLLUP, flag);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.CollectionItem#getHue()
     */
    public Long getHue() {
        return OrmliteIntegerAttributeWrapper.getValue(getPersistedItem(), ATTR_HUE);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.CollectionItem#setHue(java.lang.Long)
     */
    public void setHue(Long value) {
        OrmliteIntegerAttributeWrapper.setValue(getPersistedItem(), ATTR_HUE, value);
    }

    /**
     * Remove ItemTombstone with an itemUid equal to a given Item's uid
     * @param item
     * @return true if a tombstone was removed
     */
    public boolean removeTombstone(Item item) {
        ItemTombstone ts = new OrmliteItemTombstoneWrapper(getPersistedItem(), ((OrmliteItemWrapper)item).getPersistedItem());
        return getPersistedItem().getTombstones().remove(ts);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.CollectionItem#generateHash()
     */
    public int generateHash() {
        return getPersistedItem().getVersion();
    }

    public Item copy() {
        CollectionItem copy = new OrmliteCollectionItemWrapper();
        //copyToItem(copy);
        return copy;
    }
}
