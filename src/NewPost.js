import Navbar from "./Navbar";

const NewPost = () => {
    const createNewPost = (e) => {
        e.preventDefault();
    }
    return ( 
        <div className="new-post">
            <Navbar/>
            <div className="back">
                <h2>New Post</h2>
                <form onSubmit={createNewPost}>
                    
                    <br></br>
                    <button>Create</button>
                </form>
            </div>
        </div>
     );
}
 
export default NewPost;