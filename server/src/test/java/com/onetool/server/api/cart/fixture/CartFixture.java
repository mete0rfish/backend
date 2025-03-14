package com.onetool.server.api.cart.fixture;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.cart.Cart;
import com.onetool.server.api.cart.CartBlueprint;
import com.onetool.server.api.member.domain.Member;

import java.util.ArrayList;
import java.util.List;

public class CartFixture {

    public static Member createCartMember() {
        return Member.builder()
                .build();
    }

    public static Cart createCartWithNoMember() {
        return Cart.createCart(
                Member.builder()
                .build()
        );
    }

    public static List<Blueprint> createBlueprints(){
        List<Blueprint> blueprints = new ArrayList<>();
        blueprints.add(
                Blueprint.builder()
                        .id(1L)
                        .blueprintName("test blueprint 1")
                        .extension("test extension 1")
                        .creatorName("test creator 1")
                        .standardPrice(10000L)
                        .salePrice(2000L)
                        .build()
        );
        blueprints.add(
                Blueprint.builder()
                        .id(2L)
                        .blueprintName("test blueprint 2")
                        .extension("test extension 2")
                        .creatorName("test creator 2")
                        .standardPrice(20000L)
                        .salePrice(3000L)
                        .build()
        );
        blueprints.add(
                Blueprint.builder()
                        .id(3L)
                        .blueprintName("test blueprint 3")
                        .extension("test extension 3")
                        .creatorName("test creator 3")
                        .standardPrice(30000L)
                        .salePrice(4000L)
                        .build()
        );
        return blueprints;
    }

    public static Blueprint createBlueprintToAddInCart(){
        return Blueprint.builder()
                .id(4L)
                .blueprintName("test blueprint 4")
                .extension("test extension 4")
                .creatorName("test creator 4")
                .standardPrice(40000L)
                .salePrice(5000L)
                .build();
    }

    public static void createCartBlueprints(Cart cart, List<Blueprint> blueprints) {
        for (Blueprint blueprint : blueprints) {
            CartBlueprint.create(cart, blueprint);
        }
    }
}
