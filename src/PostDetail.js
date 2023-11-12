import { useParams } from 'react-router-dom';
import useFetch from './useFetch';
import Navbar from './Navbar';
const PostDetail = () => {
    const domain = "https://madneighborhood.tech:443";
    const {itemId} = useParams();
    const {post, error} = useFetch(`${domain}/get_item?id=${itemId}`);


    return ( 
        <div className='post-detail'>
            <Navbar/>
            { error && <div>{error}</div>}
            <article>
                <h2>{post.info}</h2>
            </article>
        </div>
     );
}
 
export default PostDetail;