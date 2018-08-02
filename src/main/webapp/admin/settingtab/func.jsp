<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>

#sortable {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 100%; 
} 

#sortable li {
	margin: 0 3px 3px 3px;
	padding-top : 0.25em;
	padding-right : 0px;
	padding-left: 1.5em;
	height: 40px;
	cursor: pointer;
	border-radius: 12px;
	width: 100%; 
}

#sortable li:hover{
	text-decoration: none;
}

#sortable li:focus{ 
	background-color: #e0e0e0;
}

#sortable li span {
	position: absolute;
	margin-left: -1.3em;
}

#sortable li p{
	font-size: 1em;
	margin: 0px;
}

#sortable li a{ 
	border : 0;
	padding: 0;
	width: max-content;
	background-color: #f6f6f6;
}

#sortable li a:visited{
	background-color: #f6f6f6; 
}

#sortable li a:focus{
	background-color: #e0e0e0;
}

#sortable li a:link{
	background-color: #f6f6f6;
}

i{
	margin-bottom: 20px;
}

/* ��� : ����, ���� : ��� */
.custom-i{
	background-color: #563d7c;
    color: #fff;
    border-radius: 6px;
    padding: 10px;
    font-weight: 700;
    margin-bottom: 7px !important;
}

.custom-hr{
	margin-top: 0;
}

/* ��� ���� div */
.sortdiv{
	padding: 15px;
	padding-top: 0; 
	margin: 0;
	border-left: 1px solid #c5c5c5;
	border-right: 1px solid #c5c5c5;
}
  
.func-sort{
	display: flex;
}

.func-sort-title{
	width: 100%; 
}

.func-sort-btn{
	width : 100%;
	text-align: right;
	padding-right: 10px;
}

/* ��� �� ���� div */
.func-detail-text-div{
	padding-top: 0;
	padding-bottom: 10px;
}

/* ��� �̹��� div */
.func-detail-image-div{
	padding: 15px;
	padding-left : 15px;
	padding-right : 15px;
	padding-top: 0;
	margin: 0;
}

.func-detail-image{
	max-height: 520px;
}

/* sort li element */
.func-element{
	pointer-events : all;
}

.func-sort-title > a:link{
	background-color: #f6f6f6; 
}

.func-sort-title > a:visited{
	background-color: #f6f6f6;
}

.func-sort-title > a:hover{
	background-color: #f6f6f6;
}
 
.nav-tabs .nav-link.active{
	border: 0px; 
}

.showImg{
	width: 100%;
	display: block;
}

.hideImg{
	display: none;
}

.popover-body > ul{
	list-style: none; 
}
</style>

<script>
$(function() {
	
	/* ���� ���� �޼ҵ� */
	function determineOrder($ul){
		$ul.children().each(function(index){
			/* OFF���� ���� li�� �� �� ���� ��ȣ�� �Ҵ��Ѵ�. */
    		if($(this).hasClass("ui-state-disabled") == false){
    			$(this).attr("funcorder", index)
    		}
    	});
	};
	
	/* popover initialize */
	$('[data-toggle="popover"]').popover({
		container : 'body',
		title : '��� ������ �����ϰ� ������ �� �־��!',
		trigger : 'hover'
	}).on('inserted.bs.popover', function(){
		var ariaDescribedby = $(this).attr("aria-describedby");
		$('#'+ariaDescribedby).css({  
			"max-width" : "600px"
		}); 
		
		var innerHTML = "<ul><li>��� ��ġ�� ������ drap & drop ������� �����ϼ���!</li><li>��ư�� ���� ����� ������ ON/OFF �ϼ���!</li></ul>";
		$(".popover-header").prepend('<i class="custom-i fas fa-question-circle"></i>');
		$(".popover-body").html(innerHTML);
	});
	
	/* sortable Event ����  ����*/
	$("#sortable").sortable({
		revert : 200,
		/* sortable�� ������ ���� �� ��. -> Element�� drag�� ��. */
		start: function(e, ui) {
	    	// $(this).attr('data-previndex', ui.item.index());
	    },
		
	    /* sort�� ��� �Ͼ �� ���� */
	    update: function(e, ui) {
	    	/* ���� Ŭ�� �� li�� �θ� ul element */
			var selectedElement = $(ui.item.context);
	    	var funcName = selectedElement.data("funcname");
	    	var $ul = selectedElement.parent();
	    	
	    	/* ������ li�� funcname�� ���� ��� �г� ����� */
	    	$(".preview_panel .preview_func_div[data-panelfuncname="+funcName+"]").fadeOut(300, function(){
	    		/* ������ li�� ���� funcname�� ���� func div */
	    		tmpElement = $(this);
	    		
	    		console.log(tmpElement);
	    		
	    		/* �ű� li�� ���ʿ� �ִ� div�� data-funcname�� �� ���� --> � ����� �� �տ� �ֳ� Ȯ����. */
	    		prevFuncName = selectedElement.prev().data("funcname");
	    		
	    		/* �ű� li�� ������ �ִ� div�� ���� */
	    		nextFuncName = selectedElement.next().data("funcname");
	    		
	    		/* li�� �����ӿ� ���� ���� ����� ��ġ�� �����̴� �κ�. */
	    		$(".preview_panel .preview_func_div").each(function(){
	    			if($(this).data("panelfuncname") == prevFuncName){
	    				console.log($(this));
	    				$(this).after(tmpElement);
	    				tmpElement.fadeIn(300);
	    			}else if(prevFuncName == undefined){
	    				/* ��� ������ �Ǿ����� ���� ��. prevFuncName == 'undefinded' �� ��Ȳ. */
	    				$(this).parent().prepend(tmpElement);  
	    				tmpElement.fadeIn(300); 
	    			}
	    		}); 
	    		
	    	});
	    	
	    	// Determine the priority.
	    	determineOrder($ul);
	    },
	    
	    /* ��� Element�� ���ؼ� sortable�� �������� �����Ѵ�. */
	    /* li���� .ul-state-disabled�� ���� ������Ʈ�� sortable ����. */
	    items : 'li:not(.ui-state-disabled)'
	});
	
	$("#sortable").disableSelection();
	
	// ù��° func li ���� ȿ��.
	$("#sortable > li:eq(0)").css({
		"background-color" : "#e0e0e0"
	}).find("a").css({
		"background-color" : "#e0e0e0"
	})
	
	/* func li Ŭ���� �̺�Ʈ */
	$("#sortable > li").click(function(event){
		var $a = $(this).find("a"); /* ������ li�� a �±�  */
		$("#sortable > li:not(this)").css("background-color","#f6f6f6");   /* ���� �� li�� css ���� */
		$("#sortable > li:not(this) a").css("background-color","#f6f6f6"); /* ���� �� li ���� a�� css ���� */
		
		/* ������ li�� ���� a�±��� css ���� */
		$a.css({"background-color" : "#e0e0e0"});
		
		/* ������ li �±��� css ���� */
		$(this).css({"background-color" : "#e0e0e0"});
		
		/* a �±׿� ����� �� Ȱ��ȭ */ 
		$a.tab("show");
		$a.attr("aria-selected", "false").removeClass("active show");
		
		/* a�±װ� ������ �ִ� data-funcname �� */
		var funcName = $a.data("funcname");
		$(".func-detail-image-div > div.tab-pane").each(function(){
			var $this = $(this);
			if($this.data("funcname")==funcName){
				$this.addClass("show active");
			}else{
				$this.removeClass("show active");
			}
		});
	});
	
	/*
		live �̺�Ʈ - ���� ������ ������Ʈ�� ���� �̺�Ʈ ����.
		on/off ��ư�� ������ ��ư�� �����ϴ� li�� ��Ȱ��ȭ(pointer-events : none;)
		��, ��ư�� pointer-events : all;�� �Ѵ�.
		�� ��, ���� css�� �������� div�� �������� ������ div��. -> on�� �� ����.
	*/
	$(document).on('click', '.func-sort-btn > div', function(){
		$li = $(this).closest("li");    /* ������ �ִ� li Ž�� */
		$ul = $(this).closest("ul");    /* ������ �ִ� ul Ž�� */
		var funcname = $li.data("funcname");
		if($(this).hasClass("off")==true){  // ��ư�� �����µ� off��� class�� ������ ���� �ʴ� -> off�ϴ� ��Ȳ.
			$li.addClass("ui-state-disabled"); 	 /* li ��Ȱ��ȭ */
			
			$li.removeAttr("funcorder");	/* funcorder ���� */
			$li.detach();				/* �ش� ��ư�� �ִ� li ���� */
			$li.appendTo($ul);		 /* ul�� �������� ������ li �߰� */
			
			/* li���� ��ư �̺�Ʈ�� Ȱ��ȭ */
			$(this).css({"pointer-events" : "all"});
			
			$(".preview_panel div").each(function(){
				if($(this).data("panelfuncname") == funcname){
					$(this).fadeOut(300);
				}
			});
			
		}else{ /* ��ư�� ������ ����� On�ϴ� ��Ȳ. */
			$li.removeClass("ui-state-disabled");
			$li.detach();				/* �ش� ��ư�� �ִ� li ���� */
			$li.prependTo($ul);
			$li.attr("funcorder", "0");
			
			/* drag & drop �� ���� ���� */
	    	var orderInfo = $("#sortable").sortable('toArray');
			
			$(".preview_panel .preview_func_div").each(function(){
				if($(this).data("panelfuncname") == funcname){ 
					var tmpElement = $(this);
					$('.preview_panel').prepend(tmpElement);
					
					/* ��������� ������ ����� panel�� ����. */
					$(this).fadeIn(300);
				}
			});
		} 
		
		// determine Order
		determineOrder($(this).closest("ul"));
	});
	
});
	
</script>
<!-- ��ɼ��� ������ --> 
<div class="inner">
	<!-- step-content -->
	<div class="row" style="height: 100%; padding: 5px;">
		<!-- ��ɿ� ���콺 ���� �� �� �� ������ ������ ����. -->
		<div class="tab-content col-sm-6 col-md-6 col-xl-6">  
			<c:forEach var="func" items="${funcs }" varStatus="stat"> 
				<c:choose>
					<c:when test="${stat.index == 0 }">
						<div class="tab-pane fade show active func-detail-text-div" id="${func.nameEng }-sort" role="tabpanel" aria-labelledby="${func.nameEng }-sort-tab">
							<i class="custom-i fas fa-comment-alt"> ��ɼҰ�</i>
							<hr class="custom-hr">
							
							<!-- name -->
							<p>��� �� : ${func.name }</p>
							<!-- description -->
							<span>${func.description }</span>
							<div class="customer-pickrate" style="margin-top: 20px;">
								<p style="margin: 0;">��� ���� ����</p>
								<div class="progress">
									<div class="progress-bar" role="progressbar" style="width: 70%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">70%</div>
								</div>
							</div> 
						</div> 
					</c:when>
					  
					<c:otherwise>
						<div class="tab-pane fade func-detail-text-div" id="${func.nameEng }-sort" role="tabpanel" aria-labelledby="${func.nameEng }-sort-tab">
							<i class="custom-i fas fa-comment-alt"> ��ɼҰ�</i>
							<hr class="custom-hr">
							<!-- name -->
							<p>��� �� : ${func.name }</p>
							<!-- description --> 
							<span>${func.description }</span>
							<div class="customer-pickrate" style="margin-top: 20px;">
								<p style="margin: 0;">��� ���� ����</p>
								<div class="progress">
									<div class="progress-bar" role="progressbar" style="width: 70%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">70%</div>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		 </div> 
		  
		<div class="sortdiv col-sm-6 col-md-6 col-xl-6"> 
			<i class="custom-i fas fa-sort" data-toggle="popover"> ��� ����</i>
			<hr class="custom-hr"> 
			<ul class="nav nav-tabs" id="sortable" class="funcsort" role="tablist">
			
			<c:forEach var="func" items="${funcs }" varStatus="stat">
				<li class="nav-item ui-state-default ui-state-disabled" id="${func.nameEng }" data-funcname="${func.nameEng }" data-funcid="${func.funcId }">
					<span class="ui-icon ui-icon-arrowthick-2-n-s"></span> 
					<!-- ui-state-disabled -->
					<div class="func-sort">
						<div class="func-sort-title"> 
							<a class="nav-link active show" id="${func.nameEng}-sort-tab" data-funcname="${func.nameEng }" data-toggle="tab" role="tab" href="#${func.nameEng}-sort" aria-controls="${func.nameEng}-sort" aria-selected="true">
								${func.name }
							</a>
						</div>
						
						<!-- �� ��ư�� li�� pointer-events�� ���� ���� ���� ���� �����ؾ� ��. -->
						<div class="func-sort-btn func-element">  
							<input type="checkbox" data-toggle="toggle" data-onstyle="success" data-offstyle="danger" data-height="20" data-size="small">
						</div>
					</div>
				</li>
			</c:forEach>
			
			</ul> 
		</div>
	</div>
</div>