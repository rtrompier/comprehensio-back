package ch.hcuge.comprehensio.entity.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;

import ch.hcuge.comprehensio.repository.LangRepository;

//@Component
//@Scope("prototype") // must not be a singleton component as it has state
public class JPAEntityResolver extends SimpleObjectIdResolver {
	//This would be JPA based object repository or you can EntityManager instance directly.
	private LangRepository objectRepository;

	@Autowired
	public JPAEntityResolver (LangRepository objectRepository) {
	    this.objectRepository = objectRepository;
	}

	@Override
	public void bindItem(IdKey id, Object pojo) {
	    super.bindItem(id, pojo);
	}

	@Override
	public Object resolveId(IdKey id) {
	    Object resolved = super.resolveId(id);
	    if (resolved == null) {
	        resolved = _tryToLoadFromSource(id);
	        bindItem(id, resolved);
	    }

	    return resolved;
	}

	private Object _tryToLoadFromSource(IdKey idKey) {

	    String id = (String) idKey.key;
	    Class<?> poType = idKey.scope;

	    return objectRepository.findById(id);
	}

	@Override
	public ObjectIdResolver newForDeserialization(Object context) {
	    return new JPAEntityResolver(objectRepository);
	}

	@Override
	public boolean canUseFor(ObjectIdResolver resolverType) {
	    return resolverType.getClass() == JPAEntityResolver.class;
	}
}