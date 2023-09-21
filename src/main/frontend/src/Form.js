export default function Form(props) {

    const handleInputChange = (setFunction, value) => {
      console.log(setFunction);
      setFunction(value);
    };

    const PropergateFunction = (e) => {
      e.preventDefault()
      props.func()
    }

    return (
        <div>
        <form id="Form" onSubmit={PropergateFunction}>

            {props.values.map((inputs) => (
                <div key={inputs.id}>
                    <label id={`label${inputs.id}`}htmlFor={inputs.id}>{inputs.display}: </label>
                    <input id={`input${inputs.id}`} type="text" 
                    onChange={(e) => handleInputChange(inputs.changeValue, e.target.value)} required 
                    />
                </div>
            ))}
          <button id="submit" type="submit">Submit</button>
        </form>
      </div>
    );
}