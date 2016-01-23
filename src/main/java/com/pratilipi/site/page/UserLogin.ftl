<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
		<style type="text/css">
	        .box{
	            text-overflow: hidden;
	            overflow: hidden;
	        }
	        .btn-default {
	            border-radius: 1px;
	        }
	        .form-control {
	            border-radius: 1px;
	        }
	        .form-horizontal {
	            max-width: 100%;
	            padding: 20px;
	        }
    	</style>
    	
    	<script>
    		function login() {
    			// Make AJAX call
    		}
    	</script>
	</head>

	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			
			<div class="box" style="min-height: 370px;">
	            <div style="margin: 20px auto; text-align: center;">
	                <p class="lead">Log In to Pratilipi!</p>
	            </div>
	            
	            <form id="userLoginForm" class="form-horizontal" action="javascript:void(0);">
	                <div class="form-group">
	                    <label for="inputEmail" class="col-sm-2 control-label">Email</label>
	                    <div class="col-sm-10">
	                        <input name="email" type="email" class="form-control" id="inputEmail" placeholder="Email">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="inputPassword" class="col-sm-2 control-label">Password</label>
	                    <div class="col-sm-10">
	                        <input name="password" type="password" class="form-control" id="inputPassword" placeholder="Password">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <div class="col-sm-offset-2 col-sm-10">
	                        
	                    </div>
	                </div>
	                <button class="btn btn-default" onclick="login()">Sign in</button>
	            </form>
	            
	        </div>
	        
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>