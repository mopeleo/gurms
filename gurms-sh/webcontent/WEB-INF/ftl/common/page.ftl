<div class="pagel">
	<input type="hidden" name="pageNo" id="pageNo" value="${result.pageNo}"/>
	<input type="hidden" name="pageSize" id="pageSize" value="${result.pageSize}"/>
	<input <#if result.firstPage>disabled="disabled"</#if> onclick="jumpPage(1)" type="button" class="button" value="起始页" />
	<input <#if result.firstPage>disabled="disabled"</#if> onclick="jumpPage(${result.prePage})" type="button" class="button" value="上一页" />
	<input <#if result.lastPage>disabled="disabled"</#if> onclick="jumpPage(${result.nextPage})" type="button" class="button" value="下一页" />
	<span class="span1">第${result.pageNo}页/</span><span>共${result.totalPages}页</span>
	<span class="span1">共${result.totalItems}条/</span><span>每页${result.pageSize}条</span>
</div>
