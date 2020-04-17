package cn.wolfcode.qo;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResult<T> {
    private int currentPage; //当前页
    private int pageSize; //每页条数
    private List<T> data; //数据
    private int totalCount; // 总数量

    //上面这些要不是传过来的 要不就是数据库查询出来的
    //下面的是计算出来的
    private int totalPage; //总页数
    private int prevPage; //上一页
    private int nextPage; //下一页

    public PageResult(int currentPage, int pageSize, List<T> data, int totalCount) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.data = data;
        this.totalCount = totalCount;

        if (totalCount <= pageSize) {
            this.totalPage = 1;
            this.prevPage = 1;
            this.nextPage = 1;
            return;
        }
        this.totalPage = this.totalCount % this.pageSize == 0 ?
                this.totalCount / this.pageSize : this.totalCount / this.pageSize + 1;
        this.prevPage = this.currentPage - 1 >= 1 ? this.currentPage - 1 : 1;
        this.nextPage = this.currentPage + 1 <= this.totalPage ? this.currentPage + 1 : this.totalPage;

    }

}
