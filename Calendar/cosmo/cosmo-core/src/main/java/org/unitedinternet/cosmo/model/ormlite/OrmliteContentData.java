///*
// * Copyright 2006 Open Source Applications Foundation
// * 
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * 
// *     http://www.apache.org/licenses/LICENSE-2.0
// * 
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.unitedinternet.cosmo.model.ormlite;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
////import javax.persistence.Column;
////import javax.persistence.Entity;
////import javax.persistence.Table;
//
//import org.apache.commons.lang.builder.ToStringBuilder;
//import org.apache.commons.lang.builder.ToStringStyle;
////import org.hibernate.annotations.Type;
//import org.unitedinternet.cosmo.CosmoIOException;
//import org.unitedinternet.cosmo.util.BufferedContent;
//
//import com.j256.ormlite.field.DataType;
//import com.j256.ormlite.field.DatabaseField;
//import com.j256.ormlite.table.DatabaseTable;
//
//
//
/////**
//// * Represents the data of a piece of Content. Data is stored
//// * as a BufferedContent, either in memory (small content) or
//// * on disk (large content).
//// */
////@Entity
////@Table(name="content_data")
//
//@DatabaseTable(tableName = "content_data")
//public class OrmliteContentData extends BaseModelObject {
//
//    private static final long serialVersionUID = -5014854905531456753L;
//    
//    //@Column(name = "content", length=102400000)
//    //@Type(type="bufferedcontent_blob")
//    //private BufferedContent content = null;
//
//    private BufferedContent content = null;
//    
//    @DatabaseField(columnName = "content", dataType = DataType.LONG_STRING)
//    private String str = null;
//    
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this,
//                ToStringStyle.MULTI_LINE_STYLE);
//    }
//
//    public BufferedContent fromString(String string) {
//        BufferedContent bufferedContent = null;
//        try {
//            new BufferedContent(new ByteArrayInputStream(string.getBytes("UTF-8")));
//        } catch (IOException e) {
//           throw new CosmoIOException(e);
//        }
//        return bufferedContent;
//    }
//
//    public String toString(BufferedContent value) {
//        final byte[] bytes;
//        bytes = DataHelper.extractBytes( value.getInputStream());
//        return PrimitiveByteArrayTypeDescriptor.INSTANCE.toString( bytes );
//    }
//
//    /**
//     * Get an InputStream to the content data.  Repeated
//     * calls to this method will return new instances
//     * of InputStream.
//     */
//    public InputStream getContentInputStream() {
//        if(content==null) {
//            return null;
//        }
//        return content.getInputStream();
//    }
//    
//    /**
//     * Set the content using an InputSteam.  Does not close the 
//     * InputStream.
//     * @param is content data
//     * @throws IOException
//     */
//    public void setContentInputStream(InputStream is) throws IOException {
//        content = new BufferedContent(is);
//    }
//    
//    /**
//     * @return the size of the data read, or -1 for no data present
//     */
//    public long getSize() {
//        if(content != null) {
//            return content.getLength();
//        }
//        else {
//            return -1;
//        }
//    } 
//}
