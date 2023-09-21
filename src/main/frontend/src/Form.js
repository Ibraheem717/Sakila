import "./Entities/css/Form.css";

export default function Form(props) {

    const handleInputChange = (setFunction, value) => {
      console.log(setFunction);
      setFunction(value);
    };

    const PropergateFunction = (e) => {
      e.preventDefault()
      props.func()
    }

    const isRequired = (inputs) => {
      if (props.required)
        return (
          <input id={`input${inputs.id}`} type="text" 
          onChange={(e) => handleInputChange(inputs.changeValue, e.target.value)} required 
          />
        );
      return (
        <input id={`input${inputs.id}`} type="text" 
        onChange={(e) => handleInputChange(inputs.changeValue, e.target.value)}  
        />
      );
    }

    return (
      <div className="main-container">
        <form id="Form" onSubmit={PropergateFunction}>

            {props.values.map((inputs) => (
                <div key={inputs.id}>
                    <label id={`label${inputs.id}`}htmlFor={inputs.id}>{inputs.display}: </label>
                    {isRequired(inputs)}
                </div>
            ))}
          <button id="submit" type="submit">Submit</button>
        </form>
      </div>
    );
}