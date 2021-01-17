import { html, render} from "./lib/lit-html.js";

class AccountView extends HTMLElement {
        
  
  constuctor() {
    this.root = this.attachShadow({mode: "open"});
  }

  get uri() {
    return this.getAttribute("uri");
  }
  
  connectedCallback() {
    console.log('fetch from URI: '+this.uri+"/all");
    fetch(this.uri+"/all").then(response => response.json()).then(json =>  {
      const table = document.createElement('div');
      render(this.template(json), table);
      this.appendChild(table);
    });
  }

  template(items) {
    return   html`
    <table border='1'>
      <tr> 
        <th>Delete</th> 
        <th>Account Id</th> 
        <th>Account Owner</th>
        <th>Lower Balance Limit</th> 
        <th>Current Balance</th> 
      </tr>
      ${items.map((item) => html`
        <tr> 
          <td>${item.balance==0 ? html`<button name=${item.id} @click=${this.delete}>X</button>` : '' }</td>
          <td>${item.id}</td>
          <td>${item.owner}</td>
          <td>${item.lowerLimit}</td>
          <td>${item.balance}</td>
        </tr>`)}
    </table>`;
  }
  
  delete(e) { 
    const accountId = e.target.name;
    const deleteUrl = "http://localhost:8080/accounts/remove?id="+accountId
    console.log('POST: '+ deleteUrl);
    fetch(deleteUrl, {method: 'post'});  
  }
  

}


customElements.define('account-grid', AccountView);