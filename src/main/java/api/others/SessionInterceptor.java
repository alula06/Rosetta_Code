/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zi sheng Wu
 */

package api.others;

import api.bean.SessionUserBean;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;


public class SessionInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation ai) throws Exception {
        return ai.invoke();
    }
    
}
