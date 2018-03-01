<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<div class="row">
    <h2>Funds</h2>
</div>
<div class="row">
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam viverra ex velit, quis mollis mauris
        feugiatac. Mauris magna quam, volutpat non nisi a, feugiat mollis turpis. Suspendisse luctus tellus id
        metus iaculis, non ultrices tellus laoreet. Cras lectus purus, lobortis vel lobortis non, hendrerit quis
        nisi.</p>
</div>
<div class="row">
    <p>You currently have <span class="price">$${user.getBalance()}</span> in funds.</p>
</div>
<%@ include file="includes/footer.jspf" %>