/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.heepay.manage.rpc.service;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-07-11")
public class WalletUsersWhere implements org.apache.thrift.TBase<WalletUsersWhere, WalletUsersWhere._Fields>, java.io.Serializable, Cloneable, Comparable<WalletUsersWhere> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("WalletUsersWhere");

  private static final org.apache.thrift.protocol.TField TIME_FROM_FIELD_DESC = new org.apache.thrift.protocol.TField("timeFrom", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TIME_TO_FIELD_DESC = new org.apache.thrift.protocol.TField("timeTo", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField CUR_PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("curPage", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField PAGE_SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("pageSize", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField MERCHANT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("merchantId", org.apache.thrift.protocol.TType.I64, (short)6);
  private static final org.apache.thrift.protocol.TField PAGE_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("pageNum", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField PHONE_FIELD_DESC = new org.apache.thrift.protocol.TField("phone", org.apache.thrift.protocol.TType.STRING, (short)8);
  private static final org.apache.thrift.protocol.TField REAL_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("realName", org.apache.thrift.protocol.TType.STRING, (short)9);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new WalletUsersWhereStandardSchemeFactory());
    schemes.put(TupleScheme.class, new WalletUsersWhereTupleSchemeFactory());
  }

  public String timeFrom; // required
  public String timeTo; // required
  public String userId; // required
  public int curPage; // required
  public int pageSize; // required
  public long merchantId; // required
  public int pageNum; // required
  public String phone; // required
  public String realName; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TIME_FROM((short)1, "timeFrom"),
    TIME_TO((short)2, "timeTo"),
    USER_ID((short)3, "userId"),
    CUR_PAGE((short)4, "curPage"),
    PAGE_SIZE((short)5, "pageSize"),
    MERCHANT_ID((short)6, "merchantId"),
    PAGE_NUM((short)7, "pageNum"),
    PHONE((short)8, "phone"),
    REAL_NAME((short)9, "realName");

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
        case 1: // TIME_FROM
          return TIME_FROM;
        case 2: // TIME_TO
          return TIME_TO;
        case 3: // USER_ID
          return USER_ID;
        case 4: // CUR_PAGE
          return CUR_PAGE;
        case 5: // PAGE_SIZE
          return PAGE_SIZE;
        case 6: // MERCHANT_ID
          return MERCHANT_ID;
        case 7: // PAGE_NUM
          return PAGE_NUM;
        case 8: // PHONE
          return PHONE;
        case 9: // REAL_NAME
          return REAL_NAME;
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
  private static final int __CURPAGE_ISSET_ID = 0;
  private static final int __PAGESIZE_ISSET_ID = 1;
  private static final int __MERCHANTID_ISSET_ID = 2;
  private static final int __PAGENUM_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TIME_FROM, new org.apache.thrift.meta_data.FieldMetaData("timeFrom", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TIME_TO, new org.apache.thrift.meta_data.FieldMetaData("timeTo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CUR_PAGE, new org.apache.thrift.meta_data.FieldMetaData("curPage", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PAGE_SIZE, new org.apache.thrift.meta_data.FieldMetaData("pageSize", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MERCHANT_ID, new org.apache.thrift.meta_data.FieldMetaData("merchantId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.PAGE_NUM, new org.apache.thrift.meta_data.FieldMetaData("pageNum", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PHONE, new org.apache.thrift.meta_data.FieldMetaData("phone", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.REAL_NAME, new org.apache.thrift.meta_data.FieldMetaData("realName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(WalletUsersWhere.class, metaDataMap);
  }

  public WalletUsersWhere() {
  }

  public WalletUsersWhere(
    String timeFrom,
    String timeTo,
    String userId,
    int curPage,
    int pageSize,
    long merchantId,
    int pageNum,
    String phone,
    String realName)
  {
    this();
    this.timeFrom = timeFrom;
    this.timeTo = timeTo;
    this.userId = userId;
    this.curPage = curPage;
    setCurPageIsSet(true);
    this.pageSize = pageSize;
    setPageSizeIsSet(true);
    this.merchantId = merchantId;
    setMerchantIdIsSet(true);
    this.pageNum = pageNum;
    setPageNumIsSet(true);
    this.phone = phone;
    this.realName = realName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public WalletUsersWhere(WalletUsersWhere other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetTimeFrom()) {
      this.timeFrom = other.timeFrom;
    }
    if (other.isSetTimeTo()) {
      this.timeTo = other.timeTo;
    }
    if (other.isSetUserId()) {
      this.userId = other.userId;
    }
    this.curPage = other.curPage;
    this.pageSize = other.pageSize;
    this.merchantId = other.merchantId;
    this.pageNum = other.pageNum;
    if (other.isSetPhone()) {
      this.phone = other.phone;
    }
    if (other.isSetRealName()) {
      this.realName = other.realName;
    }
  }

  public WalletUsersWhere deepCopy() {
    return new WalletUsersWhere(this);
  }

  @Override
  public void clear() {
    this.timeFrom = null;
    this.timeTo = null;
    this.userId = null;
    setCurPageIsSet(false);
    this.curPage = 0;
    setPageSizeIsSet(false);
    this.pageSize = 0;
    setMerchantIdIsSet(false);
    this.merchantId = 0;
    setPageNumIsSet(false);
    this.pageNum = 0;
    this.phone = null;
    this.realName = null;
  }

  public String getTimeFrom() {
    return this.timeFrom;
  }

  public WalletUsersWhere setTimeFrom(String timeFrom) {
    this.timeFrom = timeFrom;
    return this;
  }

  public void unsetTimeFrom() {
    this.timeFrom = null;
  }

  /** Returns true if field timeFrom is set (has been assigned a value) and false otherwise */
  public boolean isSetTimeFrom() {
    return this.timeFrom != null;
  }

  public void setTimeFromIsSet(boolean value) {
    if (!value) {
      this.timeFrom = null;
    }
  }

  public String getTimeTo() {
    return this.timeTo;
  }

  public WalletUsersWhere setTimeTo(String timeTo) {
    this.timeTo = timeTo;
    return this;
  }

  public void unsetTimeTo() {
    this.timeTo = null;
  }

  /** Returns true if field timeTo is set (has been assigned a value) and false otherwise */
  public boolean isSetTimeTo() {
    return this.timeTo != null;
  }

  public void setTimeToIsSet(boolean value) {
    if (!value) {
      this.timeTo = null;
    }
  }

  public String getUserId() {
    return this.userId;
  }

  public WalletUsersWhere setUserId(String userId) {
    this.userId = userId;
    return this;
  }

  public void unsetUserId() {
    this.userId = null;
  }

  /** Returns true if field userId is set (has been assigned a value) and false otherwise */
  public boolean isSetUserId() {
    return this.userId != null;
  }

  public void setUserIdIsSet(boolean value) {
    if (!value) {
      this.userId = null;
    }
  }

  public int getCurPage() {
    return this.curPage;
  }

  public WalletUsersWhere setCurPage(int curPage) {
    this.curPage = curPage;
    setCurPageIsSet(true);
    return this;
  }

  public void unsetCurPage() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CURPAGE_ISSET_ID);
  }

  /** Returns true if field curPage is set (has been assigned a value) and false otherwise */
  public boolean isSetCurPage() {
    return EncodingUtils.testBit(__isset_bitfield, __CURPAGE_ISSET_ID);
  }

  public void setCurPageIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CURPAGE_ISSET_ID, value);
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public WalletUsersWhere setPageSize(int pageSize) {
    this.pageSize = pageSize;
    setPageSizeIsSet(true);
    return this;
  }

  public void unsetPageSize() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PAGESIZE_ISSET_ID);
  }

  /** Returns true if field pageSize is set (has been assigned a value) and false otherwise */
  public boolean isSetPageSize() {
    return EncodingUtils.testBit(__isset_bitfield, __PAGESIZE_ISSET_ID);
  }

  public void setPageSizeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PAGESIZE_ISSET_ID, value);
  }

  public long getMerchantId() {
    return this.merchantId;
  }

  public WalletUsersWhere setMerchantId(long merchantId) {
    this.merchantId = merchantId;
    setMerchantIdIsSet(true);
    return this;
  }

  public void unsetMerchantId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MERCHANTID_ISSET_ID);
  }

  /** Returns true if field merchantId is set (has been assigned a value) and false otherwise */
  public boolean isSetMerchantId() {
    return EncodingUtils.testBit(__isset_bitfield, __MERCHANTID_ISSET_ID);
  }

  public void setMerchantIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MERCHANTID_ISSET_ID, value);
  }

  public int getPageNum() {
    return this.pageNum;
  }

  public WalletUsersWhere setPageNum(int pageNum) {
    this.pageNum = pageNum;
    setPageNumIsSet(true);
    return this;
  }

  public void unsetPageNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PAGENUM_ISSET_ID);
  }

  /** Returns true if field pageNum is set (has been assigned a value) and false otherwise */
  public boolean isSetPageNum() {
    return EncodingUtils.testBit(__isset_bitfield, __PAGENUM_ISSET_ID);
  }

  public void setPageNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PAGENUM_ISSET_ID, value);
  }

  public String getPhone() {
    return this.phone;
  }

  public WalletUsersWhere setPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public void unsetPhone() {
    this.phone = null;
  }

  /** Returns true if field phone is set (has been assigned a value) and false otherwise */
  public boolean isSetPhone() {
    return this.phone != null;
  }

  public void setPhoneIsSet(boolean value) {
    if (!value) {
      this.phone = null;
    }
  }

  public String getRealName() {
    return this.realName;
  }

  public WalletUsersWhere setRealName(String realName) {
    this.realName = realName;
    return this;
  }

  public void unsetRealName() {
    this.realName = null;
  }

  /** Returns true if field realName is set (has been assigned a value) and false otherwise */
  public boolean isSetRealName() {
    return this.realName != null;
  }

  public void setRealNameIsSet(boolean value) {
    if (!value) {
      this.realName = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TIME_FROM:
      if (value == null) {
        unsetTimeFrom();
      } else {
        setTimeFrom((String)value);
      }
      break;

    case TIME_TO:
      if (value == null) {
        unsetTimeTo();
      } else {
        setTimeTo((String)value);
      }
      break;

    case USER_ID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((String)value);
      }
      break;

    case CUR_PAGE:
      if (value == null) {
        unsetCurPage();
      } else {
        setCurPage((Integer)value);
      }
      break;

    case PAGE_SIZE:
      if (value == null) {
        unsetPageSize();
      } else {
        setPageSize((Integer)value);
      }
      break;

    case MERCHANT_ID:
      if (value == null) {
        unsetMerchantId();
      } else {
        setMerchantId((Long)value);
      }
      break;

    case PAGE_NUM:
      if (value == null) {
        unsetPageNum();
      } else {
        setPageNum((Integer)value);
      }
      break;

    case PHONE:
      if (value == null) {
        unsetPhone();
      } else {
        setPhone((String)value);
      }
      break;

    case REAL_NAME:
      if (value == null) {
        unsetRealName();
      } else {
        setRealName((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TIME_FROM:
      return getTimeFrom();

    case TIME_TO:
      return getTimeTo();

    case USER_ID:
      return getUserId();

    case CUR_PAGE:
      return getCurPage();

    case PAGE_SIZE:
      return getPageSize();

    case MERCHANT_ID:
      return getMerchantId();

    case PAGE_NUM:
      return getPageNum();

    case PHONE:
      return getPhone();

    case REAL_NAME:
      return getRealName();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TIME_FROM:
      return isSetTimeFrom();
    case TIME_TO:
      return isSetTimeTo();
    case USER_ID:
      return isSetUserId();
    case CUR_PAGE:
      return isSetCurPage();
    case PAGE_SIZE:
      return isSetPageSize();
    case MERCHANT_ID:
      return isSetMerchantId();
    case PAGE_NUM:
      return isSetPageNum();
    case PHONE:
      return isSetPhone();
    case REAL_NAME:
      return isSetRealName();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof WalletUsersWhere)
      return this.equals((WalletUsersWhere)that);
    return false;
  }

  public boolean equals(WalletUsersWhere that) {
    if (that == null)
      return false;

    boolean this_present_timeFrom = true && this.isSetTimeFrom();
    boolean that_present_timeFrom = true && that.isSetTimeFrom();
    if (this_present_timeFrom || that_present_timeFrom) {
      if (!(this_present_timeFrom && that_present_timeFrom))
        return false;
      if (!this.timeFrom.equals(that.timeFrom))
        return false;
    }

    boolean this_present_timeTo = true && this.isSetTimeTo();
    boolean that_present_timeTo = true && that.isSetTimeTo();
    if (this_present_timeTo || that_present_timeTo) {
      if (!(this_present_timeTo && that_present_timeTo))
        return false;
      if (!this.timeTo.equals(that.timeTo))
        return false;
    }

    boolean this_present_userId = true && this.isSetUserId();
    boolean that_present_userId = true && that.isSetUserId();
    if (this_present_userId || that_present_userId) {
      if (!(this_present_userId && that_present_userId))
        return false;
      if (!this.userId.equals(that.userId))
        return false;
    }

    boolean this_present_curPage = true;
    boolean that_present_curPage = true;
    if (this_present_curPage || that_present_curPage) {
      if (!(this_present_curPage && that_present_curPage))
        return false;
      if (this.curPage != that.curPage)
        return false;
    }

    boolean this_present_pageSize = true;
    boolean that_present_pageSize = true;
    if (this_present_pageSize || that_present_pageSize) {
      if (!(this_present_pageSize && that_present_pageSize))
        return false;
      if (this.pageSize != that.pageSize)
        return false;
    }

    boolean this_present_merchantId = true;
    boolean that_present_merchantId = true;
    if (this_present_merchantId || that_present_merchantId) {
      if (!(this_present_merchantId && that_present_merchantId))
        return false;
      if (this.merchantId != that.merchantId)
        return false;
    }

    boolean this_present_pageNum = true;
    boolean that_present_pageNum = true;
    if (this_present_pageNum || that_present_pageNum) {
      if (!(this_present_pageNum && that_present_pageNum))
        return false;
      if (this.pageNum != that.pageNum)
        return false;
    }

    boolean this_present_phone = true && this.isSetPhone();
    boolean that_present_phone = true && that.isSetPhone();
    if (this_present_phone || that_present_phone) {
      if (!(this_present_phone && that_present_phone))
        return false;
      if (!this.phone.equals(that.phone))
        return false;
    }

    boolean this_present_realName = true && this.isSetRealName();
    boolean that_present_realName = true && that.isSetRealName();
    if (this_present_realName || that_present_realName) {
      if (!(this_present_realName && that_present_realName))
        return false;
      if (!this.realName.equals(that.realName))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_timeFrom = true && (isSetTimeFrom());
    list.add(present_timeFrom);
    if (present_timeFrom)
      list.add(timeFrom);

    boolean present_timeTo = true && (isSetTimeTo());
    list.add(present_timeTo);
    if (present_timeTo)
      list.add(timeTo);

    boolean present_userId = true && (isSetUserId());
    list.add(present_userId);
    if (present_userId)
      list.add(userId);

    boolean present_curPage = true;
    list.add(present_curPage);
    if (present_curPage)
      list.add(curPage);

    boolean present_pageSize = true;
    list.add(present_pageSize);
    if (present_pageSize)
      list.add(pageSize);

    boolean present_merchantId = true;
    list.add(present_merchantId);
    if (present_merchantId)
      list.add(merchantId);

    boolean present_pageNum = true;
    list.add(present_pageNum);
    if (present_pageNum)
      list.add(pageNum);

    boolean present_phone = true && (isSetPhone());
    list.add(present_phone);
    if (present_phone)
      list.add(phone);

    boolean present_realName = true && (isSetRealName());
    list.add(present_realName);
    if (present_realName)
      list.add(realName);

    return list.hashCode();
  }

  @Override
  public int compareTo(WalletUsersWhere other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTimeFrom()).compareTo(other.isSetTimeFrom());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTimeFrom()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.timeFrom, other.timeFrom);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTimeTo()).compareTo(other.isSetTimeTo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTimeTo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.timeTo, other.timeTo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUserId()).compareTo(other.isSetUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userId, other.userId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCurPage()).compareTo(other.isSetCurPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurPage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.curPage, other.curPage);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPageSize()).compareTo(other.isSetPageSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPageSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pageSize, other.pageSize);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMerchantId()).compareTo(other.isSetMerchantId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMerchantId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.merchantId, other.merchantId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPageNum()).compareTo(other.isSetPageNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPageNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pageNum, other.pageNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPhone()).compareTo(other.isSetPhone());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPhone()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.phone, other.phone);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRealName()).compareTo(other.isSetRealName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRealName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.realName, other.realName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("WalletUsersWhere(");
    boolean first = true;

    sb.append("timeFrom:");
    if (this.timeFrom == null) {
      sb.append("null");
    } else {
      sb.append(this.timeFrom);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("timeTo:");
    if (this.timeTo == null) {
      sb.append("null");
    } else {
      sb.append(this.timeTo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("userId:");
    if (this.userId == null) {
      sb.append("null");
    } else {
      sb.append(this.userId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("curPage:");
    sb.append(this.curPage);
    first = false;
    if (!first) sb.append(", ");
    sb.append("pageSize:");
    sb.append(this.pageSize);
    first = false;
    if (!first) sb.append(", ");
    sb.append("merchantId:");
    sb.append(this.merchantId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("pageNum:");
    sb.append(this.pageNum);
    first = false;
    if (!first) sb.append(", ");
    sb.append("phone:");
    if (this.phone == null) {
      sb.append("null");
    } else {
      sb.append(this.phone);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("realName:");
    if (this.realName == null) {
      sb.append("null");
    } else {
      sb.append(this.realName);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class WalletUsersWhereStandardSchemeFactory implements SchemeFactory {
    public WalletUsersWhereStandardScheme getScheme() {
      return new WalletUsersWhereStandardScheme();
    }
  }

  private static class WalletUsersWhereStandardScheme extends StandardScheme<WalletUsersWhere> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, WalletUsersWhere struct) throws TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TIME_FROM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.timeFrom = iprot.readString();
              struct.setTimeFromIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TIME_TO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.timeTo = iprot.readString();
              struct.setTimeToIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.userId = iprot.readString();
              struct.setUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CUR_PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.curPage = iprot.readI32();
              struct.setCurPageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PAGE_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.pageSize = iprot.readI32();
              struct.setPageSizeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // MERCHANT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.merchantId = iprot.readI64();
              struct.setMerchantIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // PAGE_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.pageNum = iprot.readI32();
              struct.setPageNumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // PHONE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.phone = iprot.readString();
              struct.setPhoneIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // REAL_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.realName = iprot.readString();
              struct.setRealNameIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, WalletUsersWhere struct) throws TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.timeFrom != null) {
        oprot.writeFieldBegin(TIME_FROM_FIELD_DESC);
        oprot.writeString(struct.timeFrom);
        oprot.writeFieldEnd();
      }
      if (struct.timeTo != null) {
        oprot.writeFieldBegin(TIME_TO_FIELD_DESC);
        oprot.writeString(struct.timeTo);
        oprot.writeFieldEnd();
      }
      if (struct.userId != null) {
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeString(struct.userId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(CUR_PAGE_FIELD_DESC);
      oprot.writeI32(struct.curPage);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PAGE_SIZE_FIELD_DESC);
      oprot.writeI32(struct.pageSize);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(MERCHANT_ID_FIELD_DESC);
      oprot.writeI64(struct.merchantId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PAGE_NUM_FIELD_DESC);
      oprot.writeI32(struct.pageNum);
      oprot.writeFieldEnd();
      if (struct.phone != null) {
        oprot.writeFieldBegin(PHONE_FIELD_DESC);
        oprot.writeString(struct.phone);
        oprot.writeFieldEnd();
      }
      if (struct.realName != null) {
        oprot.writeFieldBegin(REAL_NAME_FIELD_DESC);
        oprot.writeString(struct.realName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class WalletUsersWhereTupleSchemeFactory implements SchemeFactory {
    public WalletUsersWhereTupleScheme getScheme() {
      return new WalletUsersWhereTupleScheme();
    }
  }

  private static class WalletUsersWhereTupleScheme extends TupleScheme<WalletUsersWhere> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, WalletUsersWhere struct) throws TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTimeFrom()) {
        optionals.set(0);
      }
      if (struct.isSetTimeTo()) {
        optionals.set(1);
      }
      if (struct.isSetUserId()) {
        optionals.set(2);
      }
      if (struct.isSetCurPage()) {
        optionals.set(3);
      }
      if (struct.isSetPageSize()) {
        optionals.set(4);
      }
      if (struct.isSetMerchantId()) {
        optionals.set(5);
      }
      if (struct.isSetPageNum()) {
        optionals.set(6);
      }
      if (struct.isSetPhone()) {
        optionals.set(7);
      }
      if (struct.isSetRealName()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetTimeFrom()) {
        oprot.writeString(struct.timeFrom);
      }
      if (struct.isSetTimeTo()) {
        oprot.writeString(struct.timeTo);
      }
      if (struct.isSetUserId()) {
        oprot.writeString(struct.userId);
      }
      if (struct.isSetCurPage()) {
        oprot.writeI32(struct.curPage);
      }
      if (struct.isSetPageSize()) {
        oprot.writeI32(struct.pageSize);
      }
      if (struct.isSetMerchantId()) {
        oprot.writeI64(struct.merchantId);
      }
      if (struct.isSetPageNum()) {
        oprot.writeI32(struct.pageNum);
      }
      if (struct.isSetPhone()) {
        oprot.writeString(struct.phone);
      }
      if (struct.isSetRealName()) {
        oprot.writeString(struct.realName);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, WalletUsersWhere struct) throws TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.timeFrom = iprot.readString();
        struct.setTimeFromIsSet(true);
      }
      if (incoming.get(1)) {
        struct.timeTo = iprot.readString();
        struct.setTimeToIsSet(true);
      }
      if (incoming.get(2)) {
        struct.userId = iprot.readString();
        struct.setUserIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.curPage = iprot.readI32();
        struct.setCurPageIsSet(true);
      }
      if (incoming.get(4)) {
        struct.pageSize = iprot.readI32();
        struct.setPageSizeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.merchantId = iprot.readI64();
        struct.setMerchantIdIsSet(true);
      }
      if (incoming.get(6)) {
        struct.pageNum = iprot.readI32();
        struct.setPageNumIsSet(true);
      }
      if (incoming.get(7)) {
        struct.phone = iprot.readString();
        struct.setPhoneIsSet(true);
      }
      if (incoming.get(8)) {
        struct.realName = iprot.readString();
        struct.setRealNameIsSet(true);
      }
    }
  }

}
