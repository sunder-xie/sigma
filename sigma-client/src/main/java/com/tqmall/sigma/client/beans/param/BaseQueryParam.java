package com.tqmall.sigma.client.beans.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by linjian on 17/5/12.
 */
@Getter
@Setter
public class BaseQueryParam implements Serializable {

    private int start = 0;

    private int limit = 15;
}
