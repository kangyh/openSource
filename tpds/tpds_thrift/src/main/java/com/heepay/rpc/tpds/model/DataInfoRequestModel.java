/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.heepay.rpc.tpds.model;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-02-15")
public class DataInfoRequestModel implements org.apache.thrift.TBase<DataInfoRequestModel, DataInfoRequestModel._Fields>, java.io.Serializable, Cloneable, Comparable<DataInfoRequestModel> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DataInfoRequestModel");

  private static final org.apache.thrift.protocol.TField CHECK_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("checkType", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CUSTOMER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("customerId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ACC_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("accNo", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField BEGIN_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("beginDate", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField END_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("endDate", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField BEGIN_PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("beginPage", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField END_PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("endPage", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField SHOW_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("showNum", org.apache.thrift.protocol.TType.STRING, (short)8);
  private static final org.apache.thrift.protocol.TField NOTE_FIELD_DESC = new org.apache.thrift.protocol.TField("note", org.apache.thrift.protocol.TType.STRING, (short)9);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DataInfoRequestModelStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DataInfoRequestModelTupleSchemeFactory());
  }

  public String checkType; // required
  public String customerId; // required
  public String accNo; // required
  public String beginDate; // required
  public String endDate; // required
  public String beginPage; // required
  public String endPage; // required
  public String showNum; // required
  public String note; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CHECK_TYPE((short)1, "checkType"),
    CUSTOMER_ID((short)2, "customerId"),
    ACC_NO((short)3, "accNo"),
    BEGIN_DATE((short)4, "beginDate"),
    END_DATE((short)5, "endDate"),
    BEGIN_PAGE((short)6, "beginPage"),
    END_PAGE((short)7, "endPage"),
    SHOW_NUM((short)8, "showNum"),
    NOTE((short)9, "note");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // CHECK_TYPE
          return CHECK_TYPE;
        case 2: // CUSTOMER_ID
          return CUSTOMER_ID;
        case 3: // ACC_NO
          return ACC_NO;
        case 4: // BEGIN_DATE
          return BEGIN_DATE;
        case 5: // END_DATE
          return END_DATE;
        case 6: // BEGIN_PAGE
          return BEGIN_PAGE;
        case 7: // END_PAGE
          return END_PAGE;
        case 8: // SHOW_NUM
          return SHOW_NUM;
        case 9: // NOTE
          return NOTE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CHECK_TYPE, new org.apache.thrift.meta_data.FieldMetaData("checkType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CUSTOMER_ID, new org.apache.thrift.meta_data.FieldMetaData("customerId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ACC_NO, new org.apache.thrift.meta_data.FieldMetaData("accNo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BEGIN_DATE, new org.apache.thrift.meta_data.FieldMetaData("beginDate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.END_DATE, new org.apache.thrift.meta_data.FieldMetaData("endDate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BEGIN_PAGE, new org.apache.thrift.meta_data.FieldMetaData("beginPage", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.END_PAGE, new org.apache.thrift.meta_data.FieldMetaData("endPage", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SHOW_NUM, new org.apache.thrift.meta_data.FieldMetaData("showNum", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.NOTE, new org.apache.thrift.meta_data.FieldMetaData("note", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DataInfoRequestModel.class, metaDataMap);
  }

  public DataInfoRequestModel() {
  }

  public DataInfoRequestModel(
    String checkType,
    String customerId,
    String accNo,
    String beginDate,
    String endDate,
    String beginPage,
    String endPage,
    String showNum,
    String note)
  {
    this();
    this.checkType = checkType;
    this.customerId = customerId;
    this.accNo = accNo;
    this.beginDate = beginDate;
    this.endDate = endDate;
    this.beginPage = beginPage;
    this.endPage = endPage;
    this.showNum = showNum;
    this.note = note;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DataInfoRequestModel(DataInfoRequestModel other) {
    if (other.isSetCheckType()) {
      this.checkType = other.checkType;
    }
    if (other.isSetCustomerId()) {
      this.customerId = other.customerId;
    }
    if (other.isSetAccNo()) {
      this.accNo = other.accNo;
    }
    if (other.isSetBeginDate()) {
      this.beginDate = other.beginDate;
    }
    if (other.isSetEndDate()) {
      this.endDate = other.endDate;
    }
    if (other.isSetBeginPage()) {
      this.beginPage = other.beginPage;
    }
    if (other.isSetEndPage()) {
      this.endPage = other.endPage;
    }
    if (other.isSetShowNum()) {
      this.showNum = other.showNum;
    }
    if (other.isSetNote()) {
      this.note = other.note;
    }
  }

  public DataInfoRequestModel deepCopy() {
    return new DataInfoRequestModel(this);
  }

  @Override
  public void clear() {
    this.checkType = null;
    this.customerId = null;
    this.accNo = null;
    this.beginDate = null;
    this.endDate = null;
    this.beginPage = null;
    this.endPage = null;
    this.showNum = null;
    this.note = null;
  }

  public String getCheckType() {
    return this.checkType;
  }

  public DataInfoRequestModel setCheckType(String checkType) {
    this.checkType = checkType;
    return this;
  }

  public void unsetCheckType() {
    this.checkType = null;
  }

  /** Returns true if field checkType is set (has been assigned a value) and false otherwise */
  public boolean isSetCheckType() {
    return this.checkType != null;
  }

  public void setCheckTypeIsSet(boolean value) {
    if (!value) {
      this.checkType = null;
    }
  }

  public String getCustomerId() {
    return this.customerId;
  }

  public DataInfoRequestModel setCustomerId(String customerId) {
    this.customerId = customerId;
    return this;
  }

  public void unsetCustomerId() {
    this.customerId = null;
  }

  /** Returns true if field customerId is set (has been assigned a value) and false otherwise */
  public boolean isSetCustomerId() {
    return this.customerId != null;
  }

  public void setCustomerIdIsSet(boolean value) {
    if (!value) {
      this.customerId = null;
    }
  }

  public String getAccNo() {
    return this.accNo;
  }

  public DataInfoRequestModel setAccNo(String accNo) {
    this.accNo = accNo;
    return this;
  }

  public void unsetAccNo() {
    this.accNo = null;
  }

  /** Returns true if field accNo is set (has been assigned a value) and false otherwise */
  public boolean isSetAccNo() {
    return this.accNo != null;
  }

  public void setAccNoIsSet(boolean value) {
    if (!value) {
      this.accNo = null;
    }
  }

  public String getBeginDate() {
    return this.beginDate;
  }

  public DataInfoRequestModel setBeginDate(String beginDate) {
    this.beginDate = beginDate;
    return this;
  }

  public void unsetBeginDate() {
    this.beginDate = null;
  }

  /** Returns true if field beginDate is set (has been assigned a value) and false otherwise */
  public boolean isSetBeginDate() {
    return this.beginDate != null;
  }

  public void setBeginDateIsSet(boolean value) {
    if (!value) {
      this.beginDate = null;
    }
  }

  public String getEndDate() {
    return this.endDate;
  }

  public DataInfoRequestModel setEndDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

  public void unsetEndDate() {
    this.endDate = null;
  }

  /** Returns true if field endDate is set (has been assigned a value) and false otherwise */
  public boolean isSetEndDate() {
    return this.endDate != null;
  }

  public void setEndDateIsSet(boolean value) {
    if (!value) {
      this.endDate = null;
    }
  }

  public String getBeginPage() {
    return this.beginPage;
  }

  public DataInfoRequestModel setBeginPage(String beginPage) {
    this.beginPage = beginPage;
    return this;
  }

  public void unsetBeginPage() {
    this.beginPage = null;
  }

  /** Returns true if field beginPage is set (has been assigned a value) and false otherwise */
  public boolean isSetBeginPage() {
    return this.beginPage != null;
  }

  public void setBeginPageIsSet(boolean value) {
    if (!value) {
      this.beginPage = null;
    }
  }

  public String getEndPage() {
    return this.endPage;
  }

  public DataInfoRequestModel setEndPage(String endPage) {
    this.endPage = endPage;
    return this;
  }

  public void unsetEndPage() {
    this.endPage = null;
  }

  /** Returns true if field endPage is set (has been assigned a value) and false otherwise */
  public boolean isSetEndPage() {
    return this.endPage != null;
  }

  public void setEndPageIsSet(boolean value) {
    if (!value) {
      this.endPage = null;
    }
  }

  public String getShowNum() {
    return this.showNum;
  }

  public DataInfoRequestModel setShowNum(String showNum) {
    this.showNum = showNum;
    return this;
  }

  public void unsetShowNum() {
    this.showNum = null;
  }

  /** Returns true if field showNum is set (has been assigned a value) and false otherwise */
  public boolean isSetShowNum() {
    return this.showNum != null;
  }

  public void setShowNumIsSet(boolean value) {
    if (!value) {
      this.showNum = null;
    }
  }

  public String getNote() {
    return this.note;
  }

  public DataInfoRequestModel setNote(String note) {
    this.note = note;
    return this;
  }

  public void unsetNote() {
    this.note = null;
  }

  /** Returns true if field note is set (has been assigned a value) and false otherwise */
  public boolean isSetNote() {
    return this.note != null;
  }

  public void setNoteIsSet(boolean value) {
    if (!value) {
      this.note = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CHECK_TYPE:
      if (value == null) {
        unsetCheckType();
      } else {
        setCheckType((String)value);
      }
      break;

    case CUSTOMER_ID:
      if (value == null) {
        unsetCustomerId();
      } else {
        setCustomerId((String)value);
      }
      break;

    case ACC_NO:
      if (value == null) {
        unsetAccNo();
      } else {
        setAccNo((String)value);
      }
      break;

    case BEGIN_DATE:
      if (value == null) {
        unsetBeginDate();
      } else {
        setBeginDate((String)value);
      }
      break;

    case END_DATE:
      if (value == null) {
        unsetEndDate();
      } else {
        setEndDate((String)value);
      }
      break;

    case BEGIN_PAGE:
      if (value == null) {
        unsetBeginPage();
      } else {
        setBeginPage((String)value);
      }
      break;

    case END_PAGE:
      if (value == null) {
        unsetEndPage();
      } else {
        setEndPage((String)value);
      }
      break;

    case SHOW_NUM:
      if (value == null) {
        unsetShowNum();
      } else {
        setShowNum((String)value);
      }
      break;

    case NOTE:
      if (value == null) {
        unsetNote();
      } else {
        setNote((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CHECK_TYPE:
      return getCheckType();

    case CUSTOMER_ID:
      return getCustomerId();

    case ACC_NO:
      return getAccNo();

    case BEGIN_DATE:
      return getBeginDate();

    case END_DATE:
      return getEndDate();

    case BEGIN_PAGE:
      return getBeginPage();

    case END_PAGE:
      return getEndPage();

    case SHOW_NUM:
      return getShowNum();

    case NOTE:
      return getNote();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CHECK_TYPE:
      return isSetCheckType();
    case CUSTOMER_ID:
      return isSetCustomerId();
    case ACC_NO:
      return isSetAccNo();
    case BEGIN_DATE:
      return isSetBeginDate();
    case END_DATE:
      return isSetEndDate();
    case BEGIN_PAGE:
      return isSetBeginPage();
    case END_PAGE:
      return isSetEndPage();
    case SHOW_NUM:
      return isSetShowNum();
    case NOTE:
      return isSetNote();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DataInfoRequestModel)
      return this.equals((DataInfoRequestModel)that);
    return false;
  }

  public boolean equals(DataInfoRequestModel that) {
    if (that == null)
      return false;

    boolean this_present_checkType = true && this.isSetCheckType();
    boolean that_present_checkType = true && that.isSetCheckType();
    if (this_present_checkType || that_present_checkType) {
      if (!(this_present_checkType && that_present_checkType))
        return false;
      if (!this.checkType.equals(that.checkType))
        return false;
    }

    boolean this_present_customerId = true && this.isSetCustomerId();
    boolean that_present_customerId = true && that.isSetCustomerId();
    if (this_present_customerId || that_present_customerId) {
      if (!(this_present_customerId && that_present_customerId))
        return false;
      if (!this.customerId.equals(that.customerId))
        return false;
    }

    boolean this_present_accNo = true && this.isSetAccNo();
    boolean that_present_accNo = true && that.isSetAccNo();
    if (this_present_accNo || that_present_accNo) {
      if (!(this_present_accNo && that_present_accNo))
        return false;
      if (!this.accNo.equals(that.accNo))
        return false;
    }

    boolean this_present_beginDate = true && this.isSetBeginDate();
    boolean that_present_beginDate = true && that.isSetBeginDate();
    if (this_present_beginDate || that_present_beginDate) {
      if (!(this_present_beginDate && that_present_beginDate))
        return false;
      if (!this.beginDate.equals(that.beginDate))
        return false;
    }

    boolean this_present_endDate = true && this.isSetEndDate();
    boolean that_present_endDate = true && that.isSetEndDate();
    if (this_present_endDate || that_present_endDate) {
      if (!(this_present_endDate && that_present_endDate))
        return false;
      if (!this.endDate.equals(that.endDate))
        return false;
    }

    boolean this_present_beginPage = true && this.isSetBeginPage();
    boolean that_present_beginPage = true && that.isSetBeginPage();
    if (this_present_beginPage || that_present_beginPage) {
      if (!(this_present_beginPage && that_present_beginPage))
        return false;
      if (!this.beginPage.equals(that.beginPage))
        return false;
    }

    boolean this_present_endPage = true && this.isSetEndPage();
    boolean that_present_endPage = true && that.isSetEndPage();
    if (this_present_endPage || that_present_endPage) {
      if (!(this_present_endPage && that_present_endPage))
        return false;
      if (!this.endPage.equals(that.endPage))
        return false;
    }

    boolean this_present_showNum = true && this.isSetShowNum();
    boolean that_present_showNum = true && that.isSetShowNum();
    if (this_present_showNum || that_present_showNum) {
      if (!(this_present_showNum && that_present_showNum))
        return false;
      if (!this.showNum.equals(that.showNum))
        return false;
    }

    boolean this_present_note = true && this.isSetNote();
    boolean that_present_note = true && that.isSetNote();
    if (this_present_note || that_present_note) {
      if (!(this_present_note && that_present_note))
        return false;
      if (!this.note.equals(that.note))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_checkType = true && (isSetCheckType());
    list.add(present_checkType);
    if (present_checkType)
      list.add(checkType);

    boolean present_customerId = true && (isSetCustomerId());
    list.add(present_customerId);
    if (present_customerId)
      list.add(customerId);

    boolean present_accNo = true && (isSetAccNo());
    list.add(present_accNo);
    if (present_accNo)
      list.add(accNo);

    boolean present_beginDate = true && (isSetBeginDate());
    list.add(present_beginDate);
    if (present_beginDate)
      list.add(beginDate);

    boolean present_endDate = true && (isSetEndDate());
    list.add(present_endDate);
    if (present_endDate)
      list.add(endDate);

    boolean present_beginPage = true && (isSetBeginPage());
    list.add(present_beginPage);
    if (present_beginPage)
      list.add(beginPage);

    boolean present_endPage = true && (isSetEndPage());
    list.add(present_endPage);
    if (present_endPage)
      list.add(endPage);

    boolean present_showNum = true && (isSetShowNum());
    list.add(present_showNum);
    if (present_showNum)
      list.add(showNum);

    boolean present_note = true && (isSetNote());
    list.add(present_note);
    if (present_note)
      list.add(note);

    return list.hashCode();
  }

  @Override
  public int compareTo(DataInfoRequestModel other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCheckType()).compareTo(other.isSetCheckType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCheckType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.checkType, other.checkType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCustomerId()).compareTo(other.isSetCustomerId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCustomerId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.customerId, other.customerId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAccNo()).compareTo(other.isSetAccNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAccNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.accNo, other.accNo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBeginDate()).compareTo(other.isSetBeginDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBeginDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.beginDate, other.beginDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndDate()).compareTo(other.isSetEndDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endDate, other.endDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBeginPage()).compareTo(other.isSetBeginPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBeginPage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.beginPage, other.beginPage);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndPage()).compareTo(other.isSetEndPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndPage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endPage, other.endPage);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetShowNum()).compareTo(other.isSetShowNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetShowNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.showNum, other.showNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNote()).compareTo(other.isSetNote());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNote()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.note, other.note);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("DataInfoRequestModel(");
    boolean first = true;

    sb.append("checkType:");
    if (this.checkType == null) {
      sb.append("null");
    } else {
      sb.append(this.checkType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("customerId:");
    if (this.customerId == null) {
      sb.append("null");
    } else {
      sb.append(this.customerId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("accNo:");
    if (this.accNo == null) {
      sb.append("null");
    } else {
      sb.append(this.accNo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("beginDate:");
    if (this.beginDate == null) {
      sb.append("null");
    } else {
      sb.append(this.beginDate);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("endDate:");
    if (this.endDate == null) {
      sb.append("null");
    } else {
      sb.append(this.endDate);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("beginPage:");
    if (this.beginPage == null) {
      sb.append("null");
    } else {
      sb.append(this.beginPage);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("endPage:");
    if (this.endPage == null) {
      sb.append("null");
    } else {
      sb.append(this.endPage);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("showNum:");
    if (this.showNum == null) {
      sb.append("null");
    } else {
      sb.append(this.showNum);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("note:");
    if (this.note == null) {
      sb.append("null");
    } else {
      sb.append(this.note);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class DataInfoRequestModelStandardSchemeFactory implements SchemeFactory {
    public DataInfoRequestModelStandardScheme getScheme() {
      return new DataInfoRequestModelStandardScheme();
    }
  }

  private static class DataInfoRequestModelStandardScheme extends StandardScheme<DataInfoRequestModel> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DataInfoRequestModel struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CHECK_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.checkType = iprot.readString();
              struct.setCheckTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CUSTOMER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.customerId = iprot.readString();
              struct.setCustomerIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ACC_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.accNo = iprot.readString();
              struct.setAccNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // BEGIN_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.beginDate = iprot.readString();
              struct.setBeginDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // END_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.endDate = iprot.readString();
              struct.setEndDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // BEGIN_PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.beginPage = iprot.readString();
              struct.setBeginPageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // END_PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.endPage = iprot.readString();
              struct.setEndPageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // SHOW_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.showNum = iprot.readString();
              struct.setShowNumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // NOTE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.note = iprot.readString();
              struct.setNoteIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, DataInfoRequestModel struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.checkType != null) {
        oprot.writeFieldBegin(CHECK_TYPE_FIELD_DESC);
        oprot.writeString(struct.checkType);
        oprot.writeFieldEnd();
      }
      if (struct.customerId != null) {
        oprot.writeFieldBegin(CUSTOMER_ID_FIELD_DESC);
        oprot.writeString(struct.customerId);
        oprot.writeFieldEnd();
      }
      if (struct.accNo != null) {
        oprot.writeFieldBegin(ACC_NO_FIELD_DESC);
        oprot.writeString(struct.accNo);
        oprot.writeFieldEnd();
      }
      if (struct.beginDate != null) {
        oprot.writeFieldBegin(BEGIN_DATE_FIELD_DESC);
        oprot.writeString(struct.beginDate);
        oprot.writeFieldEnd();
      }
      if (struct.endDate != null) {
        oprot.writeFieldBegin(END_DATE_FIELD_DESC);
        oprot.writeString(struct.endDate);
        oprot.writeFieldEnd();
      }
      if (struct.beginPage != null) {
        oprot.writeFieldBegin(BEGIN_PAGE_FIELD_DESC);
        oprot.writeString(struct.beginPage);
        oprot.writeFieldEnd();
      }
      if (struct.endPage != null) {
        oprot.writeFieldBegin(END_PAGE_FIELD_DESC);
        oprot.writeString(struct.endPage);
        oprot.writeFieldEnd();
      }
      if (struct.showNum != null) {
        oprot.writeFieldBegin(SHOW_NUM_FIELD_DESC);
        oprot.writeString(struct.showNum);
        oprot.writeFieldEnd();
      }
      if (struct.note != null) {
        oprot.writeFieldBegin(NOTE_FIELD_DESC);
        oprot.writeString(struct.note);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DataInfoRequestModelTupleSchemeFactory implements SchemeFactory {
    public DataInfoRequestModelTupleScheme getScheme() {
      return new DataInfoRequestModelTupleScheme();
    }
  }

  private static class DataInfoRequestModelTupleScheme extends TupleScheme<DataInfoRequestModel> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DataInfoRequestModel struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCheckType()) {
        optionals.set(0);
      }
      if (struct.isSetCustomerId()) {
        optionals.set(1);
      }
      if (struct.isSetAccNo()) {
        optionals.set(2);
      }
      if (struct.isSetBeginDate()) {
        optionals.set(3);
      }
      if (struct.isSetEndDate()) {
        optionals.set(4);
      }
      if (struct.isSetBeginPage()) {
        optionals.set(5);
      }
      if (struct.isSetEndPage()) {
        optionals.set(6);
      }
      if (struct.isSetShowNum()) {
        optionals.set(7);
      }
      if (struct.isSetNote()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetCheckType()) {
        oprot.writeString(struct.checkType);
      }
      if (struct.isSetCustomerId()) {
        oprot.writeString(struct.customerId);
      }
      if (struct.isSetAccNo()) {
        oprot.writeString(struct.accNo);
      }
      if (struct.isSetBeginDate()) {
        oprot.writeString(struct.beginDate);
      }
      if (struct.isSetEndDate()) {
        oprot.writeString(struct.endDate);
      }
      if (struct.isSetBeginPage()) {
        oprot.writeString(struct.beginPage);
      }
      if (struct.isSetEndPage()) {
        oprot.writeString(struct.endPage);
      }
      if (struct.isSetShowNum()) {
        oprot.writeString(struct.showNum);
      }
      if (struct.isSetNote()) {
        oprot.writeString(struct.note);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DataInfoRequestModel struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.checkType = iprot.readString();
        struct.setCheckTypeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.customerId = iprot.readString();
        struct.setCustomerIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.accNo = iprot.readString();
        struct.setAccNoIsSet(true);
      }
      if (incoming.get(3)) {
        struct.beginDate = iprot.readString();
        struct.setBeginDateIsSet(true);
      }
      if (incoming.get(4)) {
        struct.endDate = iprot.readString();
        struct.setEndDateIsSet(true);
      }
      if (incoming.get(5)) {
        struct.beginPage = iprot.readString();
        struct.setBeginPageIsSet(true);
      }
      if (incoming.get(6)) {
        struct.endPage = iprot.readString();
        struct.setEndPageIsSet(true);
      }
      if (incoming.get(7)) {
        struct.showNum = iprot.readString();
        struct.setShowNumIsSet(true);
      }
      if (incoming.get(8)) {
        struct.note = iprot.readString();
        struct.setNoteIsSet(true);
      }
    }
  }

}

