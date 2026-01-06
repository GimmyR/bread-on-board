type Props = {
    marginBottom: number,
    icon: string,
    type: string,
    name: string,
    placeholder: string
};

export default function SignInInput({ marginBottom, icon, type, name, placeholder }: Props) {
    return (
        <div className={`col-8 col-md-7 col-lg-6 mb-${marginBottom}`}>
            <div className="input-group">
                <span className="input-group-text">
                    <i className={`bi bi-${icon}`}></i>
                </span>
                <input type={type} name={name} className="form-control" placeholder={placeholder}/>
            </div>
        </div>
    );
}