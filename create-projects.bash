#!/usr/bin/env bash

mkdir microservices
cd microservices

spring init \
--boot-version=2.7.0 \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=user-service \
--package-name=com.haxejs.microservices.core.user \
--groupId=com.haxejs.microservices.core.user \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
user-service

spring init \
--boot-version=2.7.0 \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=product-service \
--package-name=com.haxejs.microservices.core.product \
--groupId=com.haxejs.microservices.core.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-service

spring init \
--boot-version=2.7.0 \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=review-service \
--package-name=com.haxejs.microservices.core.review \
--groupId=com.haxejs.microservices.core.review \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
review-service

spring init \
--boot-version=2.7.0 \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=recommendation-service \
--package-name=com.haxejs.microservices.core.recommendation \
--groupId=com.haxejs.microservices.core.recommendation \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
recommendation-service

spring init \
--boot-version=2.7.0 \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=product-composite-service \
--package-name=com.haxejs.microservices.composite.product \
--groupId=com.haxejs.microservices.composite.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-composite-service

cd ..
