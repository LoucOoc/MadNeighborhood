const Alert = (props) => {
    const lendings = props.lendings;
    const domain = "https://madneighborhood.tech:443";
    return ( 
        <div className="alert">
            {lendings.map ((lendingId) => {
                const lending = fetch(`${domain}/get_post?id=${lendingId}`);
                 <div className="alert-individual">
                    <h2>{lending.title}</h2> 
                    <h2>{lending.end_avail}</h2>
                 </div>
            })}
        </div>
     );
}
 
export default Alert;