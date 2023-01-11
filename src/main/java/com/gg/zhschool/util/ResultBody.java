package com.gg.zhschool.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**全局统一返回结果类
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 14:30
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class ResultBody<T> {
    @ApiModelProperty(value = "返回状态码")
    private Integer code;
    @ApiModelProperty(value = "返回信息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private T data;

    public ResultBody(){}

    /**
     * 返回数据
     * @param data
     * @param <T>
     * @return
     */
    protected static <T> ResultBody<T> build(T data){
        ResultBody<T> resultBody = new ResultBody<>();
        if (data!=null){
            resultBody.setData(data);
        }
        return resultBody;
    }
    public static <T> ResultBody<T> build(T body,ResultCodeEnum resultCodeEnum){
        ResultBody<T> resultBody = build(body);
        resultBody.setCode(resultCodeEnum.getCode());
        resultBody.setMessage(resultCodeEnum.getMessage());
        return resultBody;
    }

    public static<T> ResultBody<T> ok(){
        return ResultBody.ok(null);
    }

    /**
     * 操作成功
     * @param data
     * @param <T>
     * @return
     */
    public static<T> ResultBody<T> ok(T data){
        ResultBody<T> resultBody = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static<T> ResultBody<T> fail(){
        return ResultBody.fail(null);
    }

    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> ResultBody<T> fail(T data){
        ResultBody<T> result = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    public ResultBody<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public ResultBody<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    /**
     * 结果是否是成功？
     * @return
     */
    public boolean isOk() {
        if(this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }

}
