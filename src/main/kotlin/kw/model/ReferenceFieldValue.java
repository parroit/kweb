package kw.model;

/**
 * Author: parroit
 * Created: 08/05/12 20.37
 */
public interface ReferenceFieldValue<ReferencedType> {
    String toEngine();
    ReferencedType fromEngine(String value);
}
