package ca.ogsl.octopi.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {

  final ObjectMapper defaultObjectMapper;
  //final ObjectMapper combinedObjectMapper;

  public JacksonObjectMapperProvider() {
    defaultObjectMapper = createDefaultMapper();
    //combinedObjectMapper = createCombinedObjectMapper();
  }

  private static ObjectMapper createDefaultMapper() {
    final ObjectMapper mapper = new ObjectMapper();
    Hibernate5Module module = new Hibernate5Module();
    module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
    mapper.registerModule(module);
    mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    mapper.registerModule(new JavaTimeModule());

    return mapper;
  }

  private static ObjectMapper createCombinedObjectMapper() {
    return new ObjectMapper();
    //.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    //.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true)
    //.setAnnotationIntrospector(createJaxbJacksonAnnotationIntrospector());
  }

  @Override
  public ObjectMapper getContext(final Class<?> type) {

    //if (type == CombinedAnnotationBean.class) {
    //    return combinedObjectMapper;
    //} else {
    return defaultObjectMapper;
    //}
  }

    /*
    private static AnnotationIntrospector createJaxbJacksonAnnotationIntrospector() {

        final AnnotationIntrospector jaxbIntrospector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        final AnnotationIntrospector jacksonIntrospector = new JacksonAnnotationIntrospector();

        return AnnotationIntrospector.pair(jacksonIntrospector, jaxbIntrospector);
    }
    */
}


