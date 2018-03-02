<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<div class="row">
    <h2>Funds</h2>
</div>
<div class="row">
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tortor ante, rutrum nec lacus vel, condimentum
        tincidunt mi. Nullam eu velit eu diam tempor sollicitudin at et augue. Suspendisse eu accumsan nisi. Ut
        velit ante, efficitur vitae dapibus et, pretium at nisl. Fusce fringilla ligula purus, nec semper mi maximus
        lacinia. </p>

    <p>You currently have <span class="price">$${user.getFormattedBalance()}</span> in funds.</p>
</div>
<div class="row">
    <h2>Add funds</h2>
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tortor ante, rutrum nec lacus vel, condimentum
        tincidunt mi. Nullam eu velit eu diam tempor sollicitudin at et augue. Suspendisse eu accumsan nisi. Ut
        velit ante, efficitur vitae dapibus et, pretium at nisl. Fusce fringilla ligula purus, nec semper mi maximus
        lacinia. </p>
</div>
<div class="row">
    <form method="post" id="funds-form">
        <div class="row">
            <div class="col s12 input-field">
                <input id="amount-input" type="number" name="amount" value="50" min="1" required>
                <label for="amount-input">Amount</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12 input-field">
                <input id="owner-input" type="text" name="amount">
                <label for="owner-input">Owner</label>
            </div>
        </div>
        <div class="row">
            <div class="col s8 input-field">
                <input id="card-number-input" type="text" name="card">
                <label for="card-number-input">Card number</label>
            </div>
            <div class="col s4 input-field">
                <input id="cvv-number-input" type="text" name="cvv">
                <label for="cvv-number-input">CVV</label>
            </div>
        </div>
        <div class="row">
            <div class="col s6 input-field">
                <input type="number" id="month-number-input" name="month">
                <label for="month-number-input">Month</label>
            </div>
            <div class="col s6 input-field">
                <input type="number" id="year-number-input" name="year">
                <label for="year-number-input">Year</label>
            </div>
        </div>
        <div class="row">
            <input class="button-submit btn-large add-funds-submit" type="submit" value="Add funds">
        </div>
    </form>
</div>
<%@ include file="includes/footer.jspf" %>