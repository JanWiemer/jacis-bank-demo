import { html, render} from "./lib/lit-html.js";

class BankView extends HTMLElement {
          
  constuctor() {
    this.root = this.attachShadow({mode: "open"});
    this.setAttribute("uri","not set");
    this.setAttribute("id","BANK_VIEW");
  }

  newId = '';
  newOwner = '';
  newLowerLimit = 0;

  get uri() {
    return this.getAttribute("uri");
  }

  connectedCallback() {
    this.fetchData();
  }

  fetchData() {
    console.log('fetch from URI: '+this.uri+"/all");
    fetch(this.uri+"/all").then(response => response.json()).then(json =>  {
      const table = document.createElement('div');
	  this.childNodes.forEach(c => this.removeChild(c));
      render(this.template(json), table);
      this.appendChild(table);
    }).then(_ => {
      document.getElementById("rebook").addEventListener("click", e=>this.rebook(e) );
    });
  }

  rebook(e) { 
    e.preventDefault();
    const amount = document.getElementById("amount").value
    const fromId = document.getElementById("fromId").value
    const toId = document.getElementById("toId").value
    console.log("rebook "+amount+" from "+fromId+" to "+toId);
    const data = new URLSearchParams();
    data.append("fromId", fromId);
    data.append("toId", toId);
    data.append("amount", amount);
    const updateUrl = this.uri + "/rebook"
    console.log('POST: '+ updateUrl);
    fetch(updateUrl, {method: 'post',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'}, 
      body: data
    }).then(_ => {
	  this.fetchData();
	});  
  }
  
  delete(e, sender) { 
    const accountId = e.target.name;
    const data = new URLSearchParams();
    data.append("id", accountId);
    const deleteUrl = sender.uri + "/remove"
    console.log('POST: '+ deleteUrl);
    fetch(deleteUrl, {method: 'post',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'}, 
      body: data
    }).then(_ => {
	  sender.fetchData();
	});  
  }

  update(e, sender) {
    console.log('newId: '+ sender.newId);
    const data = new URLSearchParams();
    data.append("id", this.newId);
    data.append("owner", this.newOwner);
    data.append("lowerLimit", this.newLowerLimit);
    const updateUrl = sender.uri + "/update"
    console.log('POST: '+ updateUrl+ " target:"+e.target);
    fetch(updateUrl, {method: 'post',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'}, 
      body: data
    }).then(_ => {
	  sender.fetchData();
	});  
  }
  
  onInputId = (e) => { this.newId = e.target.value; };
  onInputOwner = (e) => { this.newOwner = e.target.value; };
  onInputLowerLimit = (e) => { this.newLowerLimit = e.target.value; };

  template(items) {
    return   html`
    <form autocomplete="off" >
      <button id="rebook" type="submit">Rebook</button>
      <input type="number" id="amount" /> from 
      <input type="text" id="fromId" /> to 
      <input type="text" id="toId" /> 
    </form>
    <table border='1'>
      <tr> 
        <th>+/-</th> 
        <th>Account Id</th> 
        <th>Account Owner</th>
        <th>Lower Balance Limit</th> 
        <th>Current Balance</th> 
      </tr>
      <tr> 
        <form autocomplete="off">         
         <td><button type="submit" @click=${(e) => this.update(e,this)}>+</button></td>
         <td><input type="text" name="id" size="16" @input=${this.onInputId}/></td>
         <td><input type="text" name="owner" size="16" @input=${this.onInputOwner}/></td>
         <td><input type="number" name="lowerLimit" size="9" @input=${this.onInputLowerLimit}/></td>
         <td><label type="number" name="balance"/>0</td>
        </form>
      </tr>
      ${items.map((item) => html`
        <tr> 
          <td>${item.balance==0 ? html`<button name=${item.id} @click=${(e) => this.delete(e,this)}>X</button>` : '' }</td>
          <td>${item.id}</td>
          <td>${item.owner}</td>
          <td>${item.lowerLimit}</td>
          <td>${item.balance}</td>
        </tr>`)}
    </table>`;
  }
  
}

customElements.define('bank-view', BankView);