/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package tr.example.data;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class Contact extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -5884508032276009694L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Contact\",\"namespace\":\"tr.example.data\",\"fields\":[{\"name\":\"type\",\"type\":\"string\"},{\"name\":\"contact_info\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Contact> ENCODER =
      new BinaryMessageEncoder<Contact>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Contact> DECODER =
      new BinaryMessageDecoder<Contact>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Contact> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Contact> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Contact> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Contact>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Contact to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Contact from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Contact instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Contact fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.CharSequence type;
  @Deprecated public java.lang.CharSequence contact_info;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Contact() {}

  /**
   * All-args constructor.
   * @param type The new value for type
   * @param contact_info The new value for contact_info
   */
  public Contact(java.lang.CharSequence type, java.lang.CharSequence contact_info) {
    this.type = type;
    this.contact_info = contact_info;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return type;
    case 1: return contact_info;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: type = (java.lang.CharSequence)value$; break;
    case 1: contact_info = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'type' field.
   * @return The value of the 'type' field.
   */
  public java.lang.CharSequence getType() {
    return type;
  }


  /**
   * Sets the value of the 'type' field.
   * @param value the value to set.
   */
  public void setType(java.lang.CharSequence value) {
    this.type = value;
  }

  /**
   * Gets the value of the 'contact_info' field.
   * @return The value of the 'contact_info' field.
   */
  public java.lang.CharSequence getContactInfo() {
    return contact_info;
  }


  /**
   * Sets the value of the 'contact_info' field.
   * @param value the value to set.
   */
  public void setContactInfo(java.lang.CharSequence value) {
    this.contact_info = value;
  }

  /**
   * Creates a new Contact RecordBuilder.
   * @return A new Contact RecordBuilder
   */
  public static tr.example.data.Contact.Builder newBuilder() {
    return new tr.example.data.Contact.Builder();
  }

  /**
   * Creates a new Contact RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Contact RecordBuilder
   */
  public static tr.example.data.Contact.Builder newBuilder(tr.example.data.Contact.Builder other) {
    if (other == null) {
      return new tr.example.data.Contact.Builder();
    } else {
      return new tr.example.data.Contact.Builder(other);
    }
  }

  /**
   * Creates a new Contact RecordBuilder by copying an existing Contact instance.
   * @param other The existing instance to copy.
   * @return A new Contact RecordBuilder
   */
  public static tr.example.data.Contact.Builder newBuilder(tr.example.data.Contact other) {
    if (other == null) {
      return new tr.example.data.Contact.Builder();
    } else {
      return new tr.example.data.Contact.Builder(other);
    }
  }

  /**
   * RecordBuilder for Contact instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Contact>
    implements org.apache.avro.data.RecordBuilder<Contact> {

    private java.lang.CharSequence type;
    private java.lang.CharSequence contact_info;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(tr.example.data.Contact.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.type)) {
        this.type = data().deepCopy(fields()[0].schema(), other.type);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.contact_info)) {
        this.contact_info = data().deepCopy(fields()[1].schema(), other.contact_info);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
    }

    /**
     * Creates a Builder by copying an existing Contact instance
     * @param other The existing instance to copy.
     */
    private Builder(tr.example.data.Contact other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.type)) {
        this.type = data().deepCopy(fields()[0].schema(), other.type);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.contact_info)) {
        this.contact_info = data().deepCopy(fields()[1].schema(), other.contact_info);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'type' field.
      * @return The value.
      */
    public java.lang.CharSequence getType() {
      return type;
    }


    /**
      * Sets the value of the 'type' field.
      * @param value The value of 'type'.
      * @return This builder.
      */
    public tr.example.data.Contact.Builder setType(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.type = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'type' field has been set.
      * @return True if the 'type' field has been set, false otherwise.
      */
    public boolean hasType() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'type' field.
      * @return This builder.
      */
    public tr.example.data.Contact.Builder clearType() {
      type = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'contact_info' field.
      * @return The value.
      */
    public java.lang.CharSequence getContactInfo() {
      return contact_info;
    }


    /**
      * Sets the value of the 'contact_info' field.
      * @param value The value of 'contact_info'.
      * @return This builder.
      */
    public tr.example.data.Contact.Builder setContactInfo(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.contact_info = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'contact_info' field has been set.
      * @return True if the 'contact_info' field has been set, false otherwise.
      */
    public boolean hasContactInfo() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'contact_info' field.
      * @return This builder.
      */
    public tr.example.data.Contact.Builder clearContactInfo() {
      contact_info = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Contact build() {
      try {
        Contact record = new Contact();
        record.type = fieldSetFlags()[0] ? this.type : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.contact_info = fieldSetFlags()[1] ? this.contact_info : (java.lang.CharSequence) defaultValue(fields()[1]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Contact>
    WRITER$ = (org.apache.avro.io.DatumWriter<Contact>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Contact>
    READER$ = (org.apache.avro.io.DatumReader<Contact>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.type);

    out.writeString(this.contact_info);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.type = in.readString(this.type instanceof Utf8 ? (Utf8)this.type : null);

      this.contact_info = in.readString(this.contact_info instanceof Utf8 ? (Utf8)this.contact_info : null);

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.type = in.readString(this.type instanceof Utf8 ? (Utf8)this.type : null);
          break;

        case 1:
          this.contact_info = in.readString(this.contact_info instanceof Utf8 ? (Utf8)this.contact_info : null);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










