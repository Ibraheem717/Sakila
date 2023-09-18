export default function Form(props) {
    const { values, handleInputChange } = props;

    return (
        <div>
        <form onSubmit={props.func}>

            {props.values.map((inputs) => (
                <div key={inputs.id}>
                    <label htmlFor={inputs.id}>{inputs.display}: </label>
                    <input type="text" id={inputs.id}
                    onChange={(e) => handleInputChange(inputs.changeValue, e.target.value)} required 
                    />
                </div>
            ))}
          <button type="submit">Submit</button>
        </form>
      </div>
    );
}