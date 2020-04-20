package com.bernardoms.model;


import io.quarkus.mongodb.panache.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@Builder
@MongoEntity(collection="shorteners")
@AllArgsConstructor
@NoArgsConstructor
public class URLShortener {
	    private ObjectId id;
	    private String originalURL;
		private String alias;
		private int redirectCount;
}
