import { Link } from "react-router-dom";

const Navbar = () => {
    return (  
        <nav className="navbar">
            <h1>MadNeighborhood</h1>    
            <div className="links">
                <Link to="/home"> Posts </Link>
                <Link to="/chat"> Chats</Link>
                <Link to="/info"> Info</Link>
            </div>
        </nav>
    );
}
 
export default Navbar;