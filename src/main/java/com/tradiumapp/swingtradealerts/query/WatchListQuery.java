
package com.tradiumapp.swingtradealerts.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.tradiumapp.swingtradealerts.auth.PrincipalManager;
import com.tradiumapp.swingtradealerts.auth.firebase.FirebaseAuthenticationToken;
import com.tradiumapp.swingtradealerts.models.Stock;
import com.tradiumapp.swingtradealerts.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class WatchListQuery implements GraphQLQueryResolver {
    @Autowired
    MongoTemplate mongoTemplate;

    @PreAuthorize("hasAuthority('permission.watchlist.admin')")
    public List<Stock> getWatchList() {
        String firebaseUid = PrincipalManager.getCurrentUserId();

        Query query1 = new Query();
        query1.addCriteria(Criteria.where("firebaseUid").is(firebaseUid));
        User user = mongoTemplate.findOne(query1, User.class);

        if (user.watchList != null) {
            Query query2 = new Query();
            query2.addCriteria(Criteria.where("symbol").in(user.watchList));
            List<Stock> stocks = mongoTemplate.find(query2, Stock.class);

            return stocks;
        } else {
            return Collections.emptyList();
        }
    }
}
