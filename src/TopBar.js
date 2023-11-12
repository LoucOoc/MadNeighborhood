import  "./App.css";
function TopBar({logo}) {
    return (<nav>
    <div class="pages">
      <img src={logo} alt="logo" class="logo"></img>
      <div class="home">Hub</div>
      <div class="chat">Chat</div>
      <div class="inventory">Your Items</div>
    </div>
    <button class="sign-up">Sign Up</button>
</nav>);
} export default TopBar;