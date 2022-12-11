<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Live Scoreboard</title>
    Hi,
    Welcome to my Live Scoreboard!

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>

</head>
<body>

<div class="container">


        <form class="form-horizontal" id="search-form">

            <label>Channel:</label>
            <div >
                <label for="title">channel Title</label>
                <input type="text" class="form-control" id="channelTitleId"/>

                <label for="ttl">TTL</label>
                <input type="number" class="form-control" id="ttlId"/>

            </div>
            </p>
            <div >
                <label for="link">Channel Link</label>
                <input type="text" class="form-control" id="channelLinkId"/>

                <label for="description">Channel Description</label>
                <input type="text" class="form-control" id="channelDescriptionId"/>
            </div>
            </p>
            <div >
                <label for="pubDate">Publish Date</label>
                <input type="text" class="form-control" id="pubDateId"/>

            </div>


            </p>
            </p>
            <label>News Item:</label>
            <div >
                <label for="title">News Title</label>
                <input type="text" class="form-control" id="newsTitleId"/>

                <label for="link">News Link</label>
                <input type="text" class="form-control" id="newLinkId"/>

            </div>
            </p>
            <div >
                <label for="newsDescription">News Description</label>
                <input type="text" class="form-control" id="newsDescriptionId"/>

                <label for="guid">News Guid</label>
                <input type="text" class="form-control" id="guidId"/>

            </div>
            </p>
            </p>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" id="bth-search"
                            class="btn btn-primary btn-lg">Search
                    </button>
                </div>
            </div>
        </form>

    </p>
    </p>
    <table id="data-table-id">
        <tr>
            <th>Channel Title</th>
            <th>Channel Description</th>
            <th>Ttl</th>
            <th>Channel Link</th>
            <th>Publish Date</th>
            <th>News Title</th>
            <th>News Link</th>
            <th>News Description</th>
            <th>Guid</th>
        </tr>

        <c:forEach items="${scoreList}" var="score">
            <tr>
                <td>${score.channelTitle}</td>
                <td>${score.channelDescription}</td>
                <td>${score.channelTtl}</td>
                <td>${score.channelLink}</td>
                <td>${score.pubDate}</td>

                <td>${score.itemTitle}</td>
                <td>${score.itemLink}</td>
                <td>${score.itemDescription}</td>
                <td>${score.itemGuid}</td>
            </tr>
        </c:forEach>


    </table>
    <c:forEach items="${allOrgBranchList}" var="loop">
        <option <c:if
                test="${loop.code == employee.organization_branch_code}">
            selected</c:if>
                value="${loop.code}">${loop.name_en}(${loop.branch_type})
        </option>
    </c:forEach>



    <script type="text/javascript">
      /*  $(document).ready(function() {
            $("#countryId").change(function() {
                var countryId = $(this).find(":selected").val();
                var json = {
                    "countryId" : countryId
                };

                $.ajax({
                    type : "POST",
                    contentType : "application/json",
                    url : "/loadCityByCountry",
                    data : JSON.stringify(json),
                    dataType : 'json',
                    cache : false,
                    timeout : 600000,
                    success : function(data) {
                        var html = '';
                        var len = data.length;
                        html += '<option value="0"></option>';
                        for (var i = 0; i < len; i++) {
                            html += '<option value="' + data[i].id + '">'
                                + data[i].name
                                + '</option>';
                        }
                        html += '</option>';
                        $('#cityId').html(html);
                    },
                    error : function(e) {
                        alert(e);
                    }
                });
            });
        });*/

      $(document).ready(function () {

          $("#search-form").submit(function (event) {

              //stop submit the form, we will post it manually.
              event.preventDefault();

              fire_ajax_submit();

          });


          function fire_ajax_submit() {

              var search = {}
              if($("#channelTitleId").val()) {
                  search["channelTitle"] = $("#channelTitleId").val()
              }
              if($("#channelDescriptionId").val()){
                  search["channelDescription"] = $("#channelDescriptionId").val()
              }
              if($("#ttlId").val()){
                  search["ttl"] = $("#ttlId").val()
              }
              if($("#channelLinkId").val()){
                  search["channelLink"] = $("#channelLinkId").val()
              }
              if($("#pubDateId").val()){
                  search["pubDate"] = $("#pubDateId").val()
              }
              if($("#newsTitleId").val()){
                  search["itemTitle"] = $("#newsTitleId").val()
              }
              if($("#newLinkId").val()){
                  search["itemLink"] = $("#newLinkId").val()
              }
              if($("#newsDescriptionId").val()){
                  search["itemDescription"] = $("#newsDescriptionId").val()
              }
              if($("#guidId").val()){
                  search["itemGuid"] = $("#guidId").val()
              }


            /*  search["channelTitle"] = $("#channelTitleId").val() ? $("#channelTitleId").val() : null;
              search["channelDescription"] = $("#channelDescriptionId").val() ? $("#channelDescriptionId").val(): null;
              search["ttl"] = $("#ttlId").val() ? $("#ttlId").val(): null;
              search["channelLink"] = $("#channelLinkId").val() ? $("#channelLinkId").val() : null;
              search["pubDate"] = $("#pubDateId").val() ? $("#pubDateId").val(): null;

              //news(channel) item
              search["itemTitle"] = $("#newsTitleId").val() ? $("#newsTitleId").val() : null;
              search["itemLink"] = $("#newLinkId").val() ? $("#newLinkId").val() : null;
              search["itemDescription"] = $("#newsDescriptionId").val() ? $("#newsDescriptionId").val() : null;
              search["itemGuid"] = $("#guidId").val() ? $("#guidId").val() : null;*/

              console.log("search:"+ JSON.stringify(search));

              var contextPath=  '${pageContext.request.contextPath}';
              var apiUrl=contextPath +"/api/score/search";

              //$("#btn-search").prop("disabled", true);

              $.ajax({
                  type: "GET",
                  contentType: "application/json",
                  url: apiUrl,
                  data: search,//JSON.stringify(search),
                  dataType: 'json',
                  cache: false,
                  //timeout: 2000,
                  success: function (data) {
                      console.log("SUCCESS... ");
                      console.table(data);
                      buildTable(data);


                  },
                  error: function (e) {

                      var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
                          + e.responseText + "&lt;/pre&gt;";
                      $('#feedback').html(json);

                      console.log("ERROR : ", e);
                      $("#btn-search").prop("disabled", false);

                  }
              });

          }

      });


      function buildTable(data){
          console.log("build table method called...");
          $("#data-table-id").empty();

          //build headers/columns
          var headers= '<tr>' +
              '<th>Channel Title</th>' +
              '<th>Channel Description</th>' +
              '<th>Ttl</th>' +
              '<th>Channel Link</th>'+
              '<th>Publish Date</th>' +
              '<th>News Title</th>'+
              '<th>News Link</th>' +
              '<th>News Description</th>'+
              '<th>Guid</th>'+
              '</tr>';

          $("#data-table-id").append(headers);

          if(data){
              for(var i=0; i<data.length; i++){
                var dataRow='<tr>';
                dataRow+= '<td>'+  data[i].channelTitle+ '</td>';
                dataRow+= '<td>'+  data[i].channelDescription+ '</td>';
                dataRow+= '<td>'+  data[i].channelTtl+ '</td>';
                dataRow+= '<td>'+  data[i].channelLink+ '</td>';
                dataRow+= '<td>'+  data[i].pubDate+ '</td>';
                dataRow+= '<td>'+  data[i].itemTitle+ '</td>';
                dataRow+= '<td>'+  data[i].itemLink+ '</td>';
                dataRow+= '<td>'+  data[i].itemDescription+ '</td>';
                dataRow+= '<td>'+  data[i].itemGuid+ '</td>';

                dataRow+='</tr>';
                  $("#data-table-id").append(dataRow);
              }
          }
      }

    </script>

</div>


</body>
</html>