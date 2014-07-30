<!doctype>
<html>
<head>
    <title>Form page</title>
</head>
<body>
<h1>Form page</h1>

<div>
    <label for="name">Name: </label> <input id='name' name='name' value='Ada Lovelace'>
</div>
<div>
    <label for="favlang">Favourite Language: </label>
    <select id='favlang' name='favlang'>
        <option></option>
        <option>Ada95</option>
        <option>Tex</option>
    </select>
</div>
<div>
    <label for="gnat">Use GNAT: </label> <input id='gnat' name='gnat' type='checkbox' value='usegnat'>
</div>

<div id='address'>
    <div>
        <label for="streetAddress">Street address: </label> <input id='streetAddress' name='streetAddress'>
    </div>
    <div>
        <label for="city">City: </label> <input id='city' name='city'>
    </div>
    <div>
        <label for="state">State: </label>
        <select id='state' name='state'>
            <option></option>
            <option value='1'>ACT</option>
            <option value='2'>NSW</option>
            <option value='3'>NT</option>
            <option value='4'>QLD</option>
            <option value='5'>TAS</option>
            <option value='6'>SA</option>
            <option value='7'>VIC</option>
            <option value='8'>WA</option>
        </select>
    </div>
    <div>
        <label for="postcode">Post code: </label> <input id='postcode' name='postcode'>
    </div>
    <div>
        <label for="regMail">Registered mail only: </label> <input id='regmail' name='regMail' type=checkbox>
    </div>
</div>
</body>
</html>