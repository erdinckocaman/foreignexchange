package com.tamplan.sample.foreignexchange.infra.spring.response;

import java.util.List;

public record ListResponse<T>(List<T> data) {}
